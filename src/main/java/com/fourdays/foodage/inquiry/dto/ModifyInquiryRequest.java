package com.fourdays.foodage.inquiry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ModifyInquiryRequest(

	@NotBlank
	@Size(max = 50)
	String title,

	@NotBlank
	@Size(max = 1500)
	String contents
) {
}
