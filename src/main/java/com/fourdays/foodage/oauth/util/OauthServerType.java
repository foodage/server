package com.fourdays.foodage.oauth.util;

import static java.util.Locale.*;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.oauth.exception.NotSupportedOauthServerTypeException;

public enum OauthServerType {

	KAKAO,
	NAVER,
	GOOGLE;

	public static OauthServerType fromName(String oauthServerName) {
		return OauthServerType.valueOf(oauthServerName.toUpperCase(ENGLISH));
	}

	@JsonCreator
	public static OauthServerType from(String jsonValue) {
		for (OauthServerType oauthServerType : values()) {
			if (oauthServerType.name().equalsIgnoreCase(jsonValue)) {
				return oauthServerType;
			}
		}
		throw new NotSupportedOauthServerTypeException(ExceptionInfo.ERR_NOT_SUPPORTED_OAUTH_SERVER_TYPE);
	}

	@JsonValue
	public String toLowerCase() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
}
