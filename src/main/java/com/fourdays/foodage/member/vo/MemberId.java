package com.fourdays.foodage.member.vo;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.member.exception.MemberIdCreateFailedException;
import com.fourdays.foodage.oauth.util.OauthServerType;

public record MemberId(

	OauthServerType oauthServerType,

	String accountEmail
) {

	public static MemberId create(OauthServerType oauthServerType, String accountEmail) {

		if (oauthServerType == null || accountEmail == null) {
			throw new MemberIdCreateFailedException(ResultCode.ERR_MEMBER_ID_CREATE_FAILED);
		}
		return new MemberId(oauthServerType, accountEmail);
	}
}
