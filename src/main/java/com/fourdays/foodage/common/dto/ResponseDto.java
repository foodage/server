package com.fourdays.foodage.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

	private final int code;
	private final String message;
	private final int httpStatus;
	private final T data;

	private static final int SUCCESS_CODE = 0;

	private static final String SUCCESS_MESSAGE = "성공";

	public static <T> ResponseDto<T> success() {
		return ResponseDto.<T>builder()
			.code(SUCCESS_CODE)
			.message(SUCCESS_MESSAGE)
			.build();
	}

	public static <T> ResponseDto<T> success(T data) {
		return ResponseDto.<T>builder()
			.code(SUCCESS_CODE)
			.message(SUCCESS_MESSAGE)
			.data(data)
			.build();
	}
}
