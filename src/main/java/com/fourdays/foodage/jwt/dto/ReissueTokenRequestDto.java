package com.fourdays.foodage.jwt.dto;

import com.fourdays.foodage.oauth.util.OauthServerType;

import jakarta.validation.constraints.NotNull;

public record ReissueTokenRequestDto(

	@NotNull
	String refreshToken,

	@NotNull
	OauthServerType oauthServerType,

	@NotNull
	String accountEmail
) {
}
