package com.fourdays.foodage.jwt.enums;

import lombok.Getter;

@Getter
public enum JwtClaim {

	OAUTH_SERVER_TYPE("oauthServerType"),
	TYPE("type"),
	ROLE("role");

	private final String value;

	JwtClaim(String value) {
		this.value = value;
	}
}
