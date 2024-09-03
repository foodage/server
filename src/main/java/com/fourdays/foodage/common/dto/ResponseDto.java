package com.fourdays.foodage.common.dto;

import org.springframework.http.HttpStatus;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

	private final int code;
	private final String message;
	private final HttpStatus httpStatus;
	private final T data;

	public static <T> ResponseDto<T> success() {
		return ResponseDto.<T>builder()
			.code(ExceptionInfo.SUCCESS.getCode())
			.message(ExceptionInfo.SUCCESS.getMessage())
			.httpStatus(ExceptionInfo.SUCCESS.getHttpStatus())
			.build();
	}

	public static <T> ResponseDto<T> success(ExceptionInfo code) {
		return ResponseDto.<T>builder()
			.code(code.getCode())
			.message(code.getMessage())
			.httpStatus(code.getHttpStatus())
			.build();
	}

	private static <T> ResponseDto<T> successData(ExceptionInfo code, T data) {
		return ResponseDto.<T>builder()
			.code(code.getCode())
			.message(code.getMessage())
			.data(data)
			.build();
	}
}
