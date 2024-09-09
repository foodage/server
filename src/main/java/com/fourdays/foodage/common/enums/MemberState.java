package com.fourdays.foodage.common.enums;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberState {

	NORMAL(0),

	TEMP_JOIN(-1), // 임시 가입 상태

	DORMANT(1), // 휴면
	PENDING_LEAVE(2), // 탈퇴 후 30일이 지나지 않아 계정 복구 가능한 상태
	LEAVE(3), // 탈퇴 완료
	BLOCK(4), // 차단
	;

	private final int code;

	@JsonValue
	public String toLowerCase() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
}
