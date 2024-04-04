package com.fourdays.foodage.oauth.dto;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.Builder;

@Builder
public record OauthLoginResponseDto(

	OauthServerType oauthServerType,
	String accountEmail,
	String accessToken,
	String nickname,
	LoginResult result,
	String credential
) {
}
