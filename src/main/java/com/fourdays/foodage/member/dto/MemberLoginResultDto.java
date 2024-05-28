package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.oauth.domain.OauthId;

public record MemberLoginResultDto(

	OauthId oauthId,
	String nickname,
	String accountEmail,
	LoginResult loginResult,
	String credential
) {
}
