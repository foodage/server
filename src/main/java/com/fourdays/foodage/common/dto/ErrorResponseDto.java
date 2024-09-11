package com.fourdays.foodage.common.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fourdays.foodage.common.exception.ExceptionInfo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto<T> {

	@Schema(
		title = "api 응답 코드",
		maxLength = 5,
		type = "int",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@Length(min = 1, max = 6)
	@NotNull
	private final int code;

	@Schema(
		title = "api 응답 메시지",
		maxLength = 255,
		requiredMode = Schema.RequiredMode.NOT_REQUIRED
	)
	@Length(min = 1, max = 255)
	@Nullable
	private final String message;

	@Schema(
		title = "api 응답 http 코드",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull
	private final HttpStatus httpStatus;

	@Schema(
		title = "api 응답 데이터",
		type = "object",
		requiredMode = Schema.RequiredMode.NOT_REQUIRED
	)
	@Nullable
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public static <T> ErrorResponseDto<T> error(ExceptionInfo errorCode) {
		return ErrorResponseDto.<T>builder()
			.code(errorCode.code())
			.message(errorCode.message())
			.httpStatus(errorCode.httpStatus())
			.build();
	}

	public static <T> ErrorResponseDto<T> error(ExceptionInfo errorCode, String errorMsg) {
		return ErrorResponseDto.<T>builder()
			.code(errorCode.code())
			.message(errorMsg)
			.httpStatus(errorCode.httpStatus())
			.build();
	}

	public static <T> ErrorResponseDto<T> error(ExceptionInfo errorCode, String errorMsg, Object... args) {
		return ErrorResponseDto.<T>builder()
			.code(errorCode.code())
			.message(String.format(errorMsg, args))
			.httpStatus(errorCode.httpStatus())
			.build();
	}
}
