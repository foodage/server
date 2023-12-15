package com.fourdays.foodage.oauth.util;

import static java.util.Locale.*;

public enum OauthServerType {

	KAKAO,
	;

	public static OauthServerType fromName(String type) {
		return OauthServerType.valueOf(type.toUpperCase(ENGLISH));
	}
}