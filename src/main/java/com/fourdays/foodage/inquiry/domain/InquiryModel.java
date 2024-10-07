package com.fourdays.foodage.inquiry.domain;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.enums.InquiryCategory;
import com.fourdays.foodage.common.enums.InquiryState;

import lombok.Getter;

@Getter
public class InquiryModel {

	// inquiry
	private Long id;

	private InquiryCategory category;

	private String title;

	private String contents;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private InquiryState state;

	// answer
	private String answer;

	private LocalDateTime answeredAt;

	public InquiryModel(Long id, InquiryCategory category, String title,
		String contents, LocalDateTime createdAt, LocalDateTime updatedAt,
		InquiryState state, String answer, LocalDateTime answeredAt) {

		this.id = id;
		this.category = category;
		this.title = title;
		this.contents = contents;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.state = state;
		this.answer = answer;
		this.answeredAt = answeredAt;
	}
}
