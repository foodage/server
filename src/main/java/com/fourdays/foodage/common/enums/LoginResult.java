package com.fourdays.foodage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginResult {

	// SUCCESS CASE
	SUCCESS(0, "로그인에 성공하였습니다."),

	// FAILED CASE
	JOIN_IN_PROGRESS(-1, "가입 진행중인 사용자입니다."),
	DORMANT_MEMBER(-2, "휴면 처리된 사용자입니다."),
	LEAVE_IN_PROGRESS(-3, "탈퇴 후 30일이 지나지 않은 사용자입니다."),
	FAILED(-99, "유효하지 않은 사용자입니다."),
	;

	private final int code;
	private final String detailMessage;
}
