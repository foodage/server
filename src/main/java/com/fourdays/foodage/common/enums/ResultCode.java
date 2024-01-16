package com.fourdays.foodage.common.enums;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;

public enum ResultCode {

	SUCCESS(0, "요청 성공", HttpStatus.OK),
	CREATED(1, "생성 성공", HttpStatus.CREATED),

	// COMMON /////////////////////////////////////////////////////////////////////////////////////////
	// exception handler
	ERR_INTERNAL(-1000, "서버에 알 수 없는 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	ERR_REQUIRED_FIELD(-1001, "입력 파라미터가 유효하지 않습니다. 파라미터를 다시 확인해주세요.", HttpStatus.BAD_REQUEST),
	ERR_ACCESS_DENIED(-1002, "해당 컨텐츠에 접근할 수 있는 권한이 없습니다.", HttpStatus.UNAUTHORIZED),

	// USER /////////////////////////////////////////////////////////////////////////////////////////
	ERR_USER_NOT_FOUND(-10000, "일치하는 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	ERR_USER_ALREADY_LEAVED(-10001, "이미 탈퇴한 회원입니다.", HttpStatus.NOT_FOUND),

	// Oauth ////////////////////////////////////////////////////////////////////////////////////////
	ERR_NOT_SUPPORTED_OAUTH_SERVER_TYPE(-11000, "지원하지 않는 간편 로그인 종류입니다.", HttpStatus.BAD_REQUEST),

	ERR_KAKAO_AUTH_CODE_REDIRECT(-11030, "인가 코드 발급 중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

	;

	private final int code; // 에러 코드
	private final String message; // 클라이언트 반환 메시지
	private final HttpStatus httpStatus; // 반환 응답 코드

	ResultCode(int code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return Optional.ofNullable(message)
			.filter(Predicate.not(String::isBlank))
			.orElse("");
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}
