package com.fourdays.foodage.inquiry.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.dto.ResponseDto;
import com.fourdays.foodage.inquiry.domain.InquiriesResponse;
import com.fourdays.foodage.inquiry.dto.CreateInquiryRequest;
import com.fourdays.foodage.inquiry.service.InquiryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "1on1-inquiry", description = "1:1 문의 관련 api")
public class InquiryController {

	private final InquiryService inquiryService;

	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}

	@PostMapping("/inquiry")
	public ResponseEntity<ResponseDto> registerInquiry(
		@RequestBody @Valid CreateInquiryRequest request) {

		inquiryService.registerInquiry(request);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(ResponseDto.success());
	}

	@GetMapping("/inquiries")
	public ResponseEntity<ResponseDto> getInquiries(
		@RequestParam("idx") @Nullable Long lastNoticeId,
		@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
	) {
		InquiriesResponse response = inquiryService.getInquiries(lastNoticeId, pageable);

		return ResponseEntity.ok(ResponseDto.success(response));
	}
}
