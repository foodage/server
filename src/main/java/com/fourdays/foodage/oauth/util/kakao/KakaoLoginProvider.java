package com.fourdays.foodage.oauth.util.kakao;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fourdays.foodage.oauth.config.KakaoConfig;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.dto.response.KakaoMember;
import com.fourdays.foodage.oauth.dto.response.KakaoToken;
import com.fourdays.foodage.oauth.util.OauthLoginProvider;
import com.fourdays.foodage.oauth.util.OauthServerType;

@Component
public class KakaoLoginProvider implements OauthLoginProvider {

	private final KakaoApiClient kakaoApiClient;
	private final KakaoConfig kakaoOauthConfig;

	public KakaoLoginProvider(KakaoApiClient kakaoApiClient, KakaoConfig kakaoOauthConfig) {
		this.kakaoApiClient = kakaoApiClient;
		this.kakaoOauthConfig = kakaoOauthConfig;
	}

	@Override
	public OauthServerType getSupportedServerType() {
		return OauthServerType.KAKAO;
	}

	@Override
	public OauthMember getTokenAndMemberInfo(String authCode) {
		KakaoToken kakaoToken = kakaoApiClient.fetchToken(tokenRequestParams(authCode));
		KakaoMember kakaoMember = kakaoApiClient.fetchMemberInfo("Bearer " + kakaoToken.accessToken());

		return kakaoMember.toOauthMember();
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