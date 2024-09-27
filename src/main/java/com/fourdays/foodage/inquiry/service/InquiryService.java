package com.fourdays.foodage.inquiry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.InvalidRequestException;
import com.fourdays.foodage.inquiry.domain.InquiriesResponse;
import com.fourdays.foodage.inquiry.domain.Inquiry;
import com.fourdays.foodage.inquiry.domain.InquiryCustomRepository;
import com.fourdays.foodage.inquiry.domain.InquiryModel;
import com.fourdays.foodage.inquiry.domain.InquiryRepository;
import com.fourdays.foodage.inquiry.dto.CreateInquiryRequest;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.vo.MemberId;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InquiryService {

	private final InquiryRepository inquiryRepository;

	private final InquiryCustomRepository inquiryCustomRepository;

	private final MemberQueryService memberQueryService;

	public InquiryService(InquiryRepository inquiryRepository, InquiryCustomRepository inquiryCustomRepository,
		MemberQueryService memberQueryService) {
		this.inquiryRepository = inquiryRepository;
		this.inquiryCustomRepository = inquiryCustomRepository;
		this.memberQueryService = memberQueryService;
	}

	public void registerInquiry(CreateInquiryRequest request) {

		Member member = null;
		Optional<MemberId> memberId = SecurityUtil.getOptionalMemberId();

		if (memberId.isPresent()) {
			// 회원인 경우
			member = memberQueryService.findByMemberId(memberId.get());
		} else {
			// 비회원인 경우
			if (StringUtil.isNullOrEmpty(request.notifyEmail())) {
				log.debug("@ Email is required for non-member inquiries.");
				throw new InvalidRequestException(ExceptionInfo.ERR_NON_MEMBER_INQUIRY_EMAIL_REQUIRED);
			}
		}

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
			.isAnswered(false)
			.build();

		inquiryRepository.save(entity);
	}

	public InquiriesResponse getInquiries(final Long idx, final Pageable pageable) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();

		int totalCount = inquiryCustomRepository.countByInquiryId(memberId);

		Slice<Long> inquiryIds =
			inquiryCustomRepository.findInquiryIds(memberId, idx, pageable);

		List<InquiryModel> inquiries =
			inquiryCustomRepository.findInquiries(inquiryIds.getContent(), pageable);

		return new InquiriesResponse(inquiryIds, inquiries, totalCount);
	}
}
