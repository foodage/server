package com.fourdays.foodage.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LoginResult {

	SUCCEEDED(0, "로그인 완료"),
	FAILED(-999, "존재하지 않는 사용자 정보");

	private final int code;
	private final String detailMessage;

	public int getCode() {
		return code;
	}
}
