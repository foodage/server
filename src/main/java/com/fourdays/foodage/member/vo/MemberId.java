package com.fourdays.foodage.member.vo;

import com.fourdays.foodage.oauth.util.OauthServerType;

public record MemberId(

	OauthServerType oauthServerType,

	String accountEmail
) {

	public static MemberId create(OauthServerType oauthServerType, String accountEmail) {
		return new MemberId(oauthServerType, accountEmail);
	}
}
