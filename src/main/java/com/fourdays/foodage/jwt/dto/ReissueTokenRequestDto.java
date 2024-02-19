package com.fourdays.foodage.jwt.dto;

public record ReissueTokenRequestDto(

	String refreshToken,
	String accountEmail
) {
}
