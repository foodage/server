package com.fourdays.foodage.member.vo;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.member.exception.MemberIdCreateFailedException;
import com.fourdays.foodage.oauth.util.OauthServerType;

public record MemberId(

	OauthServerType oauthServerType,

	String accountEmail
) {

	public static MemberId create(OauthServerType oauthServerType, String accountEmail) {

		if (oauthServerType == null || accountEmail == null) {
			throw new MemberIdCreateFailedException(ExceptionInfo.ERR_MEMBER_ID_CREATE_FAILED);
		}
		return new MemberId(oauthServerType, accountEmail);
	}
}
