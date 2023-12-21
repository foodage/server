package com.fourdays.foodage.oauth.util;

import static java.util.Locale.*;

public enum OauthServerType {

	KAKAO,
	;

	public static OauthServerType fromName(String oauthServerName) {
		return OauthServerType.valueOf(oauthServerName.toUpperCase(ENGLISH));
	}
}