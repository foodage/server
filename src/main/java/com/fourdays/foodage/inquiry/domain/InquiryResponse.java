package com.fourdays.foodage.inquiry.domain;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.enums.InquiryCategory;
import com.fourdays.foodage.common.enums.InquiryState;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InquiryResponse {

	private Long id;

	private InquiryState state;

	private InquiryGroup inquiry;

	private AnswerGroup answer;

	public InquiryResponse(Inquiry inquiry) {

		this.id = inquiry.getId();
		this.state = inquiry.getState();
		this.inquiry = InquiryGroup.builder()
			.category(inquiry.getCategory())
			.title(inquiry.getTitle())
			.contents(inquiry.getContents())
			.registeredAt(inquiry.getCreatedAt())
			.updatedAt(inquiry.getUpdatedAt())
			.build();
		this.answer = AnswerGroup.builder()
			.contents(inquiry.getAnswer())
			.registeredAt(inquiry.getAnsweredAt())
			.build();
	}

	@AllArgsConstructor
	@Builder
	@Getter
	public static class InquiryGroup {

		private InquiryCategory category;

		private String title;

		private String contents;

		private LocalDateTime registeredAt;

		private LocalDateTime updatedAt;
	}

	@AllArgsConstructor
	@Builder
	@Getter
	public static class AnswerGroup {

		private String contents;

		private LocalDateTime registeredAt;
	}
}
