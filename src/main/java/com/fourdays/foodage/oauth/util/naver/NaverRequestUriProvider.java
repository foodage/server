package com.fourdays.foodage.oauth.util.naver;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fourdays.foodage.oauth.config.NaverConfig;
import com.fourdays.foodage.oauth.util.OauthRequestUriProvider;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NaverRequestUriProvider implements OauthRequestUriProvider {

	private final NaverConfig naverConfig;

	@Override
	public OauthServerType getSupportedServerType() {
		return OauthServerType.NAVER;
	}

	@Override
	public String buildRequestUri() {
		return UriComponentsBuilder
			.fromUriString("https://nid.naver.com/oauth2.0/authorize")
			.queryParam("response_type", "code")
			.queryParam("client_id", naverConfig.clientId())
			.queryParam("redirect_uri", naverConfig.redirectUri())
			.queryParam("state", "test_state")
			.build()
			.toUriString();
	}
}