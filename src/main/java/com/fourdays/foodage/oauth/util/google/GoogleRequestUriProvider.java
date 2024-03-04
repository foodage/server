package com.fourdays.foodage.oauth.util.google;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fourdays.foodage.oauth.config.GoogleConfig;
import com.fourdays.foodage.oauth.util.OauthRequestUriProvider;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GoogleRequestUriProvider implements OauthRequestUriProvider {

	private final GoogleConfig googleConfig;

	@Override
	public OauthServerType getSupportedServerType() {
		return OauthServerType.GOOGLE;
	}

	@Override
	public String buildRequestUri() {
		return UriComponentsBuilder
			.fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
			.queryParam("response_type", "code")
			.queryParam("client_id", googleConfig.clientId())
			.queryParam("redirect_uri", googleConfig.redirectUri())
			.queryParam("scope", googleConfig.scope())
			.build()
			.toUriString();
	}
}