package com.fourdays.foodage.inquiry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.InquiryState;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.InvalidRequestException;
import com.fourdays.foodage.common.service.MailService;
import com.fourdays.foodage.inquiry.domain.InquiriesResponse;
import com.fourdays.foodage.inquiry.domain.Inquiry;
import com.fourdays.foodage.inquiry.domain.InquiryCustomRepository;
import com.fourdays.foodage.inquiry.domain.InquiryModel;
import com.fourdays.foodage.inquiry.domain.InquiryRepository;
import com.fourdays.foodage.inquiry.domain.InquiryResponse;
import com.fourdays.foodage.inquiry.dto.CreateInquiryRequest;
import com.fourdays.foodage.inquiry.dto.ModifyInquiryRequest;
import com.fourdays.foodage.inquiry.dto.RegisterAnswerRequest;
import com.fourdays.foodage.inquiry.exception.InquiryAlreadyAnsweredException;
import com.fourdays.foodage.inquiry.exception.InquiryNotFoundException;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.notice.exception.NoticeDeleteException;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InquiryService {

	private final InquiryRepository inquiryRepository;

	private final InquiryCustomRepository inquiryCustomRepository;

	private final MemberQueryService memberQueryService;

	private final MailService mailService;

	public InquiryService(InquiryRepository inquiryRepository, InquiryCustomRepository inquiryCustomRepository,
		MemberQueryService memberQueryService, MailService mailService) {
		this.inquiryRepository = inquiryRepository;
		this.inquiryCustomRepository = inquiryCustomRepository;
		this.memberQueryService = memberQueryService;
		this.mailService = mailService;
	}

	public void registerInquiry(CreateInquiryRequest request) {

		Optional<MemberId> memberId = SecurityUtil.getOptionalMemberId();
		if (memberId.isEmpty() &&
			StringUtil.isNullOrEmpty(request.notifyEmail())) {
			// 비회원인데 알림받을 이메일 주소를 입력하지 않았을 경우
			log.debug("@ Email is required for non-member inquiries.");
			throw new InvalidRequestException(ExceptionInfo.ERR_NON_MEMBER_INQUIRY_EMAIL_REQUIRED);
		}

		Member member = memberQueryService.findByMemberId(memberId.get());
		Inquiry entity = Inquiry.builder()
			.category(request.category())
			.title(request.title())
			.contents(request.contents())
			.notifyEmail(request.notifyEmail() != null
				? request.notifyEmail()
				: member.getAccountEmail())
			.createdBy(member != null
				? member.getId()
				: null)
			.state(InquiryState.PENDING)
			.build();

		inquiryRepository.save(entity);

		mailService.sendInquiryNotiMail(entity.getTitle(), entity.getContents());
	}

	@Transactional
	public void registerAnswer(final Long id, final RegisterAnswerRequest request) {

		final MemberId memberId = SecurityUtil.getCurrentMemberId();
		final Member member = memberQueryService.findByMemberId(memberId);
		final Inquiry inquiry = getInquiryById(id);

		final boolean isFirstAnswer = inquiry.getState() == InquiryState.PENDING;

		// 답변 등록
		inquiry.registerAnswer(request.contents(), member.getId());

		if (isFirstAnswer) {
			final boolean isMemberInquiry = inquiry.getCreatedBy() != null;
			mailService.sendAnswerNotiMail(inquiry.getId(), inquiry.getNotifyEmail(),
				inquiry.getTitle(), inquiry.getAnswer(), isMemberInquiry);
		}
	}

	public InquiriesResponse getInquiries(final Long idx, final Pageable pageable) {

		final MemberId memberId = SecurityUtil.getCurrentMemberId();

		final int totalCount = inquiryCustomRepository.countByInquiryId(memberId);
		final Slice<Long> inquiryIds =
			inquiryCustomRepository.findInquiryIds(memberId, idx, pageable);
		final List<InquiryModel> inquiries =
			inquiryCustomRepository.findInquiries(inquiryIds.getContent(), pageable);

		if (inquiries == null || inquiries.isEmpty()) {
			return new InquiriesResponse();
		}
		return new InquiriesResponse(inquiryIds, inquiries, totalCount);
	}

	public InquiryResponse getInquiryDetail(final Long id) {

		final MemberId memberId = SecurityUtil.getCurrentMemberId();
		final Inquiry inquiry = getInquiryByIdAndWriter(id, memberId);

		return new InquiryResponse(inquiry);
	}

	public void modifyInquiry(final Long id, final ModifyInquiryRequest request) {

		final MemberId memberId = SecurityUtil.getCurrentMemberId();
		final Inquiry inquiry = getInquiryByIdAndWriter(id, memberId);

		validateIsSameMember(memberId, inquiry.getCreatedBy(), inquiry.getId());
		// 답변이 완료된 상태에서는 문의 내용을 수정할 수 없음
		if (inquiry.getState() == InquiryState.ANSWERED) {
			throw new InquiryAlreadyAnsweredException(ExceptionInfo.ERR_INQUIRY_ALREADY_ANSWERED);
		}

		inquiry.modifyInquiry(request.title(), request.contents());
	}

	public void deleteInquiry(final Long id) {

		final MemberId memberId = SecurityUtil.getCurrentMemberId();
		final Inquiry inquiry = getInquiryByIdAndWriter(id, memberId);

		validateIsSameMember(memberId, inquiry.getCreatedBy(), inquiry.getId());

		log.info("* category  : {}", inquiry.getCategory());
		log.info("* title     : {}", inquiry.getTitle());
		log.info("* content   : {}", inquiry.getContents());
		log.info("* createdAt : {}", inquiry.getCreatedAt());
		log.info("* creator   : {}", inquiry.getCreatedBy());
		log.info("* requester : [{}] {}", memberId.oauthServerType(), memberId.accountEmail());
		log.info("*--------------------------------------*");

		inquiryRepository.delete(inquiry);
	}

	//////////////////////////////////////////////////////////////////

	// 수정, 삭제 등의 작업 시 생성자와 요청자 정보가 같은지 비교할 때 사용
	public void validateIsSameMember(MemberId memberId, Long createdBy, Long resourceId) {

		Member requester = memberQueryService.findByMemberId(memberId);
		if (createdBy != requester.getId()) {
			log.error("* --- deletion failed : Creator ID [{}] does not match Requester ID [{}] for Resource ID [{}]",
				createdBy, requester.getId(), resourceId);
			log.error("* requester info : [{}] {}", memberId.oauthServerType(), memberId.accountEmail());
			throw new NoticeDeleteException(ExceptionInfo.ERR_UNAUTHORIZED_DELETE_REQUEST);
		}

		log.info("\n*--------- delete notice info ---------*");
		log.info("* id        : {}", resourceId);
	}

	//////////////////////////////////////////////////////////////////

	private Inquiry getInquiryById(final Long id) {

		return inquiryRepository.findById(id)
			.orElseThrow(() -> new InquiryNotFoundException(ExceptionInfo.ERR_INQUIRY_NOT_FOUND));
	}

	private Inquiry getInquiryByIdAndWriter(final Long id, final MemberId memberId) {

		Inquiry inquiry = inquiryCustomRepository.findInquiry(id, memberId);
		if (inquiry == null) {
			throw new InquiryNotFoundException(ExceptionInfo.ERR_INQUIRY_NOT_FOUND);
		}
		return inquiry;
	}
}
