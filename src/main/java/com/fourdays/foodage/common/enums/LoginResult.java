package com.fourdays.foodage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginResult {

	SUCCEEDED(0, "로그인이 완료되었습니다."),
	FAILED(-999, "존재하지 않는 사용자 정보입니다.");

	private final int code;
	private final String detailMessage;
}
