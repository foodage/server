package com.fourdays.foodage.notice.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.notice.domain.Notice;
import com.fourdays.foodage.notice.domain.NoticeCustomRepository;
import com.fourdays.foodage.notice.domain.NoticeRepository;
import com.fourdays.foodage.notice.domain.model.NoticeModel;
import com.fourdays.foodage.notice.dto.CreateNoticeRequest;
import com.fourdays.foodage.notice.dto.NoticeResponse;
import com.fourdays.foodage.notice.exception.NoticeDeleteException;
import com.fourdays.foodage.notice.exception.NoticeNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeService {

	private final NoticeRepository noticeRepository;

	private final NoticeCustomRepository noticeCustomRepository;

	private final MemberQueryService memberQueryService;

	public NoticeService(NoticeRepository noticeRepository, NoticeCustomRepository noticeCustomRepository,
		MemberQueryService memberQueryService) {
		this.noticeRepository = noticeRepository;
		this.noticeCustomRepository = noticeCustomRepository;
		this.memberQueryService = memberQueryService;
	}

	public void addNotice(final CreateNoticeRequest request, final MemberId memberId) {

		Member member = memberQueryService.findByMemberId(memberId);
		Notice notice = new Notice(request, member.getId());
		noticeRepository.save(notice);
	}

	public NoticeResponse getNotices(final Long idx, final Pageable pageable) {

		int totalCount = noticeCustomRepository.countByNoticeId();

		Slice<Long> noticeIds =
			noticeCustomRepository.findNoticeIds(idx, pageable);

		List<NoticeModel> notices =
			noticeCustomRepository.findNotices(noticeIds.getContent(), pageable);

		return new NoticeResponse(noticeIds, notices, totalCount);
	}

	public NoticeModel getNoticeDetail(final Long id) {

		Notice notice = getNoticeById(id);
		notice.addViews();

		return noticeCustomRepository.findNoticeDetailById(id);
	}

	public void deleteNotice(final Long id, final MemberId memberId) {

		Notice notice = getNoticeById(id);
		Member requester = memberQueryService.findByMemberId(memberId);
		if (notice.getCreatedBy() != requester.getId()) {
			log.error("* --- deletion failed : Creator ID [{}] does not match Requester ID [{}] for Resource ID [{}]",
				notice.getCreatedBy(), requester.getId(), notice.getId());
			log.error("* requester info : [{}] {}", memberId.oauthServerType(), memberId.accountEmail());
			throw new NoticeDeleteException(ExceptionInfo.ERR_UNAUTHORIZED_DELETE_REQUEST);
		}

		log.info("\n*--------- delete notice info ---------*");
		log.info("* id        : {}", notice.getId());
		log.info("* title     : {}", notice.getTitle());
		log.info("* content   : {}", notice.getContents());
		log.info("* category  : {}", notice.getCategory());
		log.info("* views     : {}", notice.getViews());
		log.info("* createdAt : {}", notice.getCreatedAt());
		log.info("* creator   : {}", notice.getCreatedBy());
		log.info("* requester : [{}] {}", memberId.oauthServerType(), memberId.accountEmail());
		log.info("*--------------------------------------*");

		noticeRepository.delete(notice);
	}

	//////////////////////////////////////////////////////////////////

	private Notice getNoticeById(final Long id) {

		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new NoticeNotFoundException(ExceptionInfo.ERR_NOTICE_NOT_FOUND));

		return notice;
	}
}
