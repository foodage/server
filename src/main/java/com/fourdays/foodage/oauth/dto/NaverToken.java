package com.fourdays.foodage.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(value = SnakeCaseStrategy.class)
public record NaverToken(

	String accessToken,
	String refreshToken,
	String tokenType,
	Integer expiresIn,
	String error,
	String errorDescription
) {
}