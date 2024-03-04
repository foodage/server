package com.fourdays.foodage.oauth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth.google")
public record GoogleConfig(
	String redirectUri,
	String clientId,
	String clientSecret,
	String[] scope
) {
}