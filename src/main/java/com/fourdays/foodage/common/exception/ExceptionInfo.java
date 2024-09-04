package com.fourdays.foodage.common.exception;

import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.http.HttpStatus;

public enum ExceptionInfo {

	SUCCESS(0, "요청 성공", HttpStatus.OK),
	CREATED(1, "생성 성공", HttpStatus.CREATED),

	// COMMON /////////////////////////////////////////////////////////////////////////////////////////
	// 1000~
	ERR_INTERNAL(-1000, "서버에 알 수 없는 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	ERR_REQUIRED_FIELD(-1001, "입력 파라미터가 유효하지 않습니다. 파라미터를 다시 확인해주세요.", HttpStatus.BAD_REQUEST),
	ERR_ACCESS_DENIED(-1002, "해당 컨텐츠에 접근할 수 있는 권한이 없습니다.", HttpStatus.UNAUTHORIZED),

	// JWT ////////////////////////////////////////////////////////////////////////////////////////////
	// 1200~
	ERR_INVALID_JWT(-1200, "유효하지 않은 토큰입니다.", HttpStatus.BAD_REQUEST),
	ERR_BLOCKED_REFRESH_TOKEN(-1201, "사용이 만료된 토큰으로는 재발행이 불가능합니다.", HttpStatus.BAD_REQUEST),

	// MEMBER /////////////////////////////////////////////////////////////////////////////////////////
	// 10000~
	ERR_MEMBER_NOT_FOUND(-10000, "일치하는 사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	ERR_MEMBER_ALREADY_JOINED(-10001, "이미 가입된 사용자입니다.", HttpStatus.CONFLICT),
	ERR_MEMBER_ALREADY_LEAVED(-10002, "이미 탈퇴한 사용자입니다.", HttpStatus.NOT_FOUND),
	ERR_MEMBER_INVALID_STATE(-10003, "유효하지 않은 사용자입니다.", HttpStatus.NOT_FOUND),
	ERR_NOT_SUPPORTED_CHARACTER_TYPE(-10004, "지원하지 않는 캐릭터 종류입니다.", HttpStatus.BAD_REQUEST),
	ERR_MISMATCH_ACCOUNT_EMAIL(-10005, "로그인 계정의 이메일과 가입자 이메일이 다릅니다.", HttpStatus.BAD_REQUEST),
	ERR_UNEXPECTED_JOIN(-10006, "비정상적인 접근입니다. 로그인 후 다시 시도해주세요.", HttpStatus.BAD_REQUEST),
	ERR_JOIN_IN_PROGRESS(-10007, "가입 진행중인 사용자입니다.", HttpStatus.CONFLICT),
	ERR_DUPLICATE_NICKNAME(-10008, "이미 사용중인 닉네임입니다.", HttpStatus.CONFLICT),
	ERR_MEMBER_ID_CREATE_FAILED(-10009, "사용자 식별값을 생성할 수 없습니다.", HttpStatus.BAD_REQUEST),
	ERR_MEMBER_NOT_IN_PENDING_LEAVE(-10002, "탈퇴 처리가 진행 중이지 않은 사용자입니다.", HttpStatus.BAD_REQUEST),

	// HOME /////////////////////////////////////////////////////////////////////////////////////////
	// 10500~
	ERR_NOT_SUPPORTED_VIEW_TYPE(-10500, "지원하지 않는 조회 종류입니다.", HttpStatus.BAD_REQUEST),

	// REVIEW //////////////////////////////////////////////////////////////////////////////////////
	// 11000~
	ERR_THUMBNAIL_NOT_FOUND(-11000, "썸네일 정보가 없습니다.", HttpStatus.NOT_FOUND),

	// OAUTH //////////////////////////////////////////////////////////////////////////////////////////
	// 10100~
	ERR_NOT_SUPPORTED_OAUTH_SERVER_TYPE(-10100, "지원하지 않는 간편 로그인 종류입니다.", HttpStatus.BAD_REQUEST),
	ERR_NOT_FOUND_OAUTH_MEMBER(-10102, "간편 로그인 정보를 가져올 수 없습니다.", HttpStatus.BAD_REQUEST);

	private final int code; // 에러 코드
	private final String message; // 클라이언트 반환 메시지
	private final HttpStatus httpStatus; // 반환 응답 코드

	ExceptionInfo(int code, String message, HttpStatus httpStatus) {
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
