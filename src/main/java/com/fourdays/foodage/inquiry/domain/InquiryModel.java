package com.fourdays.foodage.inquiry.domain;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.enums.InquiryCategory;

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

	// answer
	private Boolean isAnswered;

	private String answer;

	private LocalDateTime answeredAt;

	public InquiryModel(Long id, InquiryCategory category, String title,
		String contents, LocalDateTime createdAt, LocalDateTime updatedAt,
		Boolean isAnswered, String answer, LocalDateTime answeredAt) {

		this.id = id;
		this.category = category;
		this.title = title;
		this.contents = contents;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.isAnswered = isAnswered;
		this.answer = answer;
		this.answeredAt = answeredAt;
	}
}
