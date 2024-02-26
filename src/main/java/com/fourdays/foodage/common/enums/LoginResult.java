package com.fourdays.foodage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginResult {

	// SUCCESS CASE
	JOINED(0, "로그인에 성공하였습니다."),

	// FAILED CASE
	JOIN_IN_PROGRESS(-1, "가입 진행중인 사용자입니다."),
	NOT_JOINED(-2, "가입되지 않은 사용자입니다."),
	BLOCKED(-3, "제한된 사용자입니다."),
	LEAVED(-4, "탈퇴한 사용자입니다."),
	INVALID(-5, "유효하지 않은 사용자입니다.");

	private final int code;
	private final String detailMessage;
}
