package com.fourdays.foodage.common.enums;

public enum MemberState {

	NORMAL(0),
	BLOCK(1),
	LEAVE(2);

	private final int code;

	MemberState(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
