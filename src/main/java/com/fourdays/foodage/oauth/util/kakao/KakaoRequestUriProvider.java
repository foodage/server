package com.fourdays.foodage.oauth.util.kakao;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fourdays.foodage.oauth.config.KakaoConfig;
import com.fourdays.foodage.oauth.util.OauthRequestUriProvider;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class KakaoRequestUriProvider implements OauthRequestUriProvider {

	private final KakaoConfig kakaoOauthConfig;

	public OauthServerType getSupportedServerType() {
		return OauthServerType.KAKAO;
	}

	public String buildRequestUri() {
		return UriComponentsBuilder
			.fromUriString("https://kauth.kakao.com/oauth/authorize")
			.queryParam("response_type", "code")
			.queryParam("client_id", kakaoOauthConfig.clientId())
			.queryParam("redirect_uri", kakaoOauthConfig.redirectUri())
			.queryParam("scope", String.join(",", kakaoOauthConfig.scope()))
			.toUriString();
	}
}