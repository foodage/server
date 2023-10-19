package com.fourdays.foodage.common.dto;

import org.springframework.http.HttpStatus;

import com.fourdays.foodage.common.enums.ResultCode;

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
			.code(ResultCode.SUCCESS.getCode())
			.message(ResultCode.SUCCESS.getMessage())
			.httpStatus(ResultCode.SUCCESS.getHttpStatus())
			.build();
	}

	public static <T> ResponseDto<T> success(ResultCode code) {
		return ResponseDto.<T>builder()
			.code(code.getCode())
			.message(code.getMessage())
			.httpStatus(code.getHttpStatus())
			.build();
	}

	private static <T> ResponseDto<T> successData(ResultCode code, T data) {
		return ResponseDto.<T>builder()
			.code(code.getCode())
			.message(code.getMessage())
			.data(data)
			.build();
	}
}
