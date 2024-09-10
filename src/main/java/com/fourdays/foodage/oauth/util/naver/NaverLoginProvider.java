package com.fourdays.foodage.oauth.util.naver;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fourdays.foodage.oauth.config.NaverConfig;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.dto.NaverMemberResponseDto;
import com.fourdays.foodage.oauth.dto.NaverToken;
import com.fourdays.foodage.oauth.util.OauthLoginProvider;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NaverLoginProvider implements OauthLoginProvider {

	private final NaverApiClient naverApiClient;
	private final NaverConfig naverOauthConfig;

	public NaverLoginProvider(NaverApiClient naverApiClient, NaverConfig naverOauthConfig) {
		this.naverApiClient = naverApiClient;
		this.naverOauthConfig = naverOauthConfig;
	}

	@Override
	public OauthServerType getSupportedServerType() {
		return OauthServerType.NAVER;
	}

	@Override
	public OauthMember getTokenAndMemberInfo(String authCode) {
		NaverToken naverToken = naverApiClient.fetchToken(tokenRequestParams(authCode));
		log.debug("* naver accessToken : {}", naverToken.accessToken());
		NaverMemberResponseDto naverMemberResponse = naverApiClient.fetchMember(
				"Bearer " + naverToken.accessToken())
			.withAccessToken(naverToken.accessToken());

		return naverMemberResponse.toOauthMember(); // = toDomain()
	}

	@Override
	public OauthMember getMemberInfo(String accessToken) {
		NaverMemberResponseDto naverMemberResponse = naverApiClient.fetchMember(
				"Bearer " + accessToken)
			.withAccessToken(accessToken);

		return naverMemberResponse.toOauthMember(); // = toDomain()
	}

	private MultiValueMap<String, String> tokenRequestParams(String authCode) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", naverOauthConfig.clientId());
		params.add("client_secret", naverOauthConfig.clientSecret());
		params.add("code", authCode);
		params.add("state", naverOauthConfig.state());
		return params;
	}
}
