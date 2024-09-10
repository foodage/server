package com.fourdays.foodage.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.experimental.Accessors;

public enum ExceptionInfo {

	// COMMON /////////////////////////////////////////////////////////////////////////////////////////
	// 1000~
	ERR_INTERNAL(-1000, "서버에 알 수 없는 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
	ERR_REQUIRED_FIELD(-1001, "입력 파라미터가 유효하지 않습니다. 파라미터를 다시 확인해주세요.", HttpStatus.BAD_REQUEST),
	ERR_ACCESS_DENIED(-1002, "해당 컨텐츠에 접근할 수 있는 권한이 없습니다.", HttpStatus.UNAUTHORIZED),

	// OAUTH //////////////////////////////////////////////////////////////////////////////////////////
	// 1200~
	ERR_NOT_SUPPORTED_OAUTH_SERVER_TYPE(-1200, "지원하지 않는 간편 로그인 종류입니다.", HttpStatus.BAD_REQUEST),
	ERR_NOT_FOUND_OAUTH_MEMBER(-1201, "간편 로그인 정보를 가져올 수 없습니다.", HttpStatus.BAD_REQUEST),

	// JWT ////////////////////////////////////////////////////////////////////////////////////////////
	// 1300~
	ERR_INVALID_JWT(-1300, "유효하지 않은 토큰입니다.", HttpStatus.BAD_REQUEST),
	ERR_BLOCKED_REFRESH_TOKEN(-1301, "사용이 만료된 토큰으로는 재발행이 불가능합니다.", HttpStatus.BAD_REQUEST),

	// REDIS ////////////////////////////////////////////////////////////////////////////////////////////
	// 1400~
	ERR_REDIS_KEY_NOT_FOUND(-1400, "해당 컨텐츠에 접근할 수 있는 권한이 없습니다.", HttpStatus.UNAUTHORIZED),

	// MEMBER /////////////////////////////////////////////////////////////////////////////////////////
	// 1500~
	ERR_MEMBER_NOT_FOUND(-1500, "일치하는 사용자 정보를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	ERR_MEMBER_ALREADY_JOINED(-1501, "이미 가입된 사용자입니다.", HttpStatus.CONFLICT),
	ERR_MEMBER_ALREADY_LEAVED(-1502, "이미 탈퇴한 사용자입니다.", HttpStatus.NOT_FOUND),
	ERR_MEMBER_INVALID_STATE(-1503, "유효하지 않은 사용자입니다.", HttpStatus.NOT_FOUND),
	ERR_NOT_SUPPORTED_CHARACTER_TYPE(-1504, "지원하지 않는 캐릭터 종류입니다.", HttpStatus.BAD_REQUEST),
	ERR_MISMATCH_ACCOUNT_EMAIL(-1505, "로그인 계정의 이메일과 가입자 이메일이 다릅니다.", HttpStatus.BAD_REQUEST),
	ERR_UNEXPECTED_JOIN(-1506, "비정상적인 접근입니다. 로그인 후 다시 시도해주세요.", HttpStatus.BAD_REQUEST),
	ERR_JOIN_IN_PROGRESS(-1507, "가입 진행중인 사용자입니다.", HttpStatus.CONFLICT),
	ERR_DUPLICATE_NICKNAME(-1508, "이미 사용중인 닉네임입니다.", HttpStatus.CONFLICT),
	ERR_MEMBER_ID_CREATE_FAILED(-1509, "사용자 식별값을 생성할 수 없습니다.", HttpStatus.BAD_REQUEST),
	ERR_MEMBER_NOT_IN_PENDING_LEAVE(-1510, "탈퇴 처리가 진행 중이지 않은 사용자입니다.", HttpStatus.BAD_REQUEST),
	ERR_MEMBER_LEAVE_FAILED(-1511, "탈퇴 처리 중 문제가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

	// HOME /////////////////////////////////////////////////////////////////////////////////////////
	// 1800~
	ERR_NOT_SUPPORTED_VIEW_TYPE(-1800, "지원하지 않는 조회 종류입니다.", HttpStatus.BAD_REQUEST),

	// REVIEW //////////////////////////////////////////////////////////////////////////////////////
	// 1900~
	ERR_THUMBNAIL_NOT_FOUND(-1900, "썸네일 정보가 없습니다.", HttpStatus.NOT_FOUND),

	// NOTICE //////////////////////////////////////////////////////////////////////////////////////
	// 2200~
	ERR_NOTICE_NOT_FOUND(-2200, "일치하는 공지사항을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
	ERR_UNAUTHORIZED_DELETE_REQUEST(-2201, "삭제 요청이 거부되었습니다. 작성자와 삭제 요청자 정보가 일치하지 않습니다.", HttpStatus.FORBIDDEN),

	;

	@Getter
	@Accessors(fluent = true)
	private final int code; // 에러 코드

	@Getter
	@Accessors(fluent = true)
	private final String message; // 클라이언트 반환 메시지

	@Getter
	@Accessors(fluent = true)
	private final HttpStatus httpStatus; // 반환 응답 코드

	ExceptionInfo(int code, String message, HttpStatus httpStatus) {
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
