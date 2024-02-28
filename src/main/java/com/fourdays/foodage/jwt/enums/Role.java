package com.fourdays.foodage.jwt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

	MEMBER("ROLE_MEMBER"), // 로그인 완료된 회원
	ADMIN("ROLE_ADMIN"); // 관리자

	private final String role;
}
