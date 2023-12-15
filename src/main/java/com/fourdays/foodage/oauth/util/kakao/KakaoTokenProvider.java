package com.fourdays.foodage.oauth.util.kakao;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fourdays.foodage.oauth.config.KakaoConfig;
import com.fourdays.foodage.oauth.controller.response.KakaoMemberResponse;
import com.fourdays.foodage.oauth.controller.response.KakaoToken;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.util.OauthServerType;
import com.fourdays.foodage.oauth.util.OauthTokenProvider;

@Component
public class KakaoTokenProvider implements OauthTokenProvider {

	private final KakaoApiClient kakaoApiClient;
	private final KakaoConfig kakaoOauthConfig;

	public KakaoTokenProvider(KakaoApiClient kakaoApiClient, KakaoConfig kakaoOauthConfig) {
		this.kakaoApiClient = kakaoApiClient;
		this.kakaoOauthConfig = kakaoOauthConfig;
	}

	@Override
	public OauthServerType getSupportedServerType() {
		return OauthServerType.KAKAO;
	}

	@Override
	public OauthMember getOAuthToken(String authCode) {
		KakaoToken kakaoToken = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
		KakaoMemberResponse kakaoMemberResponse =
			kakaoApiClient.fetchMember("Bearer " + kakaoToken.accessToken());
		return kakaoMemberResponse.toDomain();
	}

	private MultiValueMap<String, String> tokenRequestParams(String authCode) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", kakaoOauthConfig.clientId());
		params.add("redirect_uri", kakaoOauthConfig.redirectUri());
		params.add("code", authCode);
		params.add("client_secret", kakaoOauthConfig.clientSecret());
		return params;
	}
}