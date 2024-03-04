package com.fourdays.foodage.jwt.dto;

public record TokenDto(

	String accessToken,
	String refreshToken
) {
}