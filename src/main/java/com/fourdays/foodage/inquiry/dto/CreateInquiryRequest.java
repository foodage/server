package com.fourdays.foodage.inquiry.dto;

import org.springframework.lang.Nullable;

import com.fourdays.foodage.common.enums.InquiryCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateInquiryRequest(

	@NotNull
	InquiryCategory category,

	@NotBlank
	@Size(max = 50)
	String title,

	@NotBlank
	@Size(max = 1500)
	String contents,

	@Nullable
	@Pattern(
		regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
		message = "유효한 이메일 주소를 입력해주세요."
	)
	String notifyEmail
) {
}
