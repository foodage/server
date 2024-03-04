package com.fourdays.foodage.oauth.dto;

import com.fourdays.foodage.common.enums.LoginResult;

import lombok.Builder;

@Builder
public record OauthLoginResponseDto(
	String accountEmail,
	String accessToken,
	String nickname,
	LoginResult result,
	String credential
) {
}
