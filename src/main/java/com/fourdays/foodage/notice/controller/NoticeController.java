package com.fourdays.foodage.notice.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.dto.ResponseDto;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.notice.domain.model.NoticeModel;
import com.fourdays.foodage.notice.dto.CreateNoticeRequest;
import com.fourdays.foodage.notice.dto.ModifyNoticeRequest;
import com.fourdays.foodage.notice.dto.NoticeResponse;
import com.fourdays.foodage.notice.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "notice", description = "공지사항 관련 api")
public class NoticeController {

	private final NoticeService noticeService;

	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	@Operation(summary = "공지사항 등록")
	@PostMapping("/notice")
	public ResponseEntity<ResponseDto<NoticeModel>> addNotice(
		@RequestBody CreateNoticeRequest request) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();

		// todo: 최상단 고정 여부 추가
		noticeService.addNotice(request, memberId);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ResponseDto.success());
	}

	@Operation(summary = "공지사항 목록 조회")
	@GetMapping("/notices")
	public ResponseEntity<ResponseDto<NoticeResponse>> getNotices(
		@RequestParam("idx") @Nullable Long lastNoticeId,
		@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
	) {
		NoticeResponse response = noticeService.getNotices(lastNoticeId, pageable);

		return ResponseEntity.ok()
			.body(ResponseDto.success(response));
	}

	@Operation(summary = "공지사항 상세 조회")
	@GetMapping("/notice/{id}")
	public ResponseEntity getNotice(@PathVariable("id") Long id) {

		NoticeModel response = noticeService.getNoticeDetail(id);

		return ResponseEntity.ok()
			.body(ResponseDto.success(response));
	}

	@Operation(summary = "공지사항 수정")
	@PatchMapping("/notice/{id}")
	public ResponseEntity modifyNotice(@PathVariable("id") Long id,
		@RequestBody ModifyNoticeRequest request) {

		noticeService.modifyNotice(id, request);

		return ResponseEntity.ok()
			.body(ResponseDto.success());
	}

	@Operation(summary = "공지사항 삭제")
	@DeleteMapping("/notice/{id}")
	public ResponseEntity deleteNotice(@PathVariable("id") Long id) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();

		noticeService.deleteNotice(id, memberId);

		return ResponseEntity.ok()
			.body(ResponseDto.success());
	}
}
