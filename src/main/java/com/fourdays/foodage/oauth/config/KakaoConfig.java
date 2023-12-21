package com.fourdays.foodage.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoConfig(
	String redirectUri,
	String clientId,
	String clientSecret,
	String[] scope
) {
}