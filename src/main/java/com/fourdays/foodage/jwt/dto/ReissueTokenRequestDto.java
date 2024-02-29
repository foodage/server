package com.fourdays.foodage.jwt.dto;

import jakarta.validation.constraints.NotNull;

public record ReissueTokenRequestDto(

	@NotNull
	String refreshToken,

	@NotNull
	String oauthServerName,

	@NotNull
	String accountEmail
) {
}
