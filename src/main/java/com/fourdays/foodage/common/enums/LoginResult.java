package com.fourdays.foodage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginResult {

	SUCCEEDED(0, "로그인에 성공하였습니다."),
	FAILED(-1, "존재하지 않는 사용자 정보입니다."),
	INVALID(-2, "휴면 처리된 사용자입니다."),
	BLOCKED(-3, "제한된 사용자입니다."),
	LEAVED(-4, "탈퇴한 사용자입니다.");

	private final int code;
	private final String detailMessage;
}
