package com.fourdays.foodage.notice.dto;

import com.fourdays.foodage.common.enums.NoticeCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ModifyNoticeRequest(

	String title,

	@NotBlank
	@Size(max = 1500)
	String contents,

	@NotNull
	NoticeCategory category
) {
}
