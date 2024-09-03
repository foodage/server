package com.fourdays.foodage.common.dto;

import org.springframework.http.HttpStatus;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto<T> {

	private final int code;
	private final String message;
	private final HttpStatus httpStatus;
	private T data;

	public static <T> ErrorResponseDto<T> error(ExceptionInfo errorCode) {
		return ErrorResponseDto.<T>builder()
			.code(errorCode.getCode())
			.message(errorCode.getMessage())
			.httpStatus(errorCode.getHttpStatus())
			.build();
	}

	public static <T> ErrorResponseDto<T> error(ExceptionInfo errorCode, String errorMsg) {
		return ErrorResponseDto.<T>builder()
			.code(errorCode.getCode())
			.message(errorMsg)
			.httpStatus(errorCode.getHttpStatus())
			.build();
	}

	public static <T> ErrorResponseDto<T> error(ExceptionInfo errorCode, String errorMsg, Object... args) {
		return ErrorResponseDto.<T>builder()
			.code(errorCode.getCode())
			.message(String.format(errorMsg, args))
			.httpStatus(errorCode.getHttpStatus())
			.build();
	}
}
