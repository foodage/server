package com.fourdays.foodage.oauth.util;

import static java.util.Locale.*;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OauthServerType {

	KAKAO,
	NAVER,
	GOOGLE;

	public static OauthServerType fromName(String oauthServerName) {
		return OauthServerType.valueOf(oauthServerName.toUpperCase(ENGLISH));
	}

	@JsonCreator
	public static OauthServerType from(String value) {
		for (OauthServerType type : values()) {
			if (type.name().toLowerCase().equals(value)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid OauthServerType value: " + value);
	}
}