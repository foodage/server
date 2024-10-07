package com.fourdays.foodage.inquiry.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterAnswerRequest(

	@NotBlank
	@Size(max = 1500)
	String contents
) {
}
