package com.fourdays.foodage.common.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto<T> {

	@Schema(
		title = "api 응답 코드",
		maxLength = 5,
		type = "int",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@Length(min = 1, max = 6)
	@NotNull
	private final Integer code;

	@Schema(
		title = "api 응답 메시지",
		maxLength = 255,
		requiredMode = Schema.RequiredMode.NOT_REQUIRED
	)
	@Length(min = 1, max = 255)
	@Nullable
	private final String message;

	@Schema(
		title = "api 응답 HTTP 코드",
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
