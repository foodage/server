package com.fourdays.foodage.oauth.util;

import static java.util.Locale.*;

public enum OauthServerType {

	KAKAO,
	NAVER,
	;

	public static OauthServerType fromName(String oauthServerName) {
		return OauthServerType.valueOf(oauthServerName.toUpperCase(ENGLISH));
	}
}