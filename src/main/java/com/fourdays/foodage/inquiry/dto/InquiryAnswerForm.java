package com.fourdays.foodage.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class InquiryAnswerForm {

	private String notifyEmail;

	private String title;

	private String contents;

	private String redirectUrl;
}
