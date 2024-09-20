package com.fourdays.foodage.oauth.util.google;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fourdays.foodage.oauth.config.GoogleConfig;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.dto.GoogleMemberResponseDto;
import com.fourdays.foodage.oauth.dto.GoogleToken;
import com.fourdays.foodage.oauth.util.OauthLoginProvider;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class GoogleLoginProvider implements OauthLoginProvider {

	private final GoogleApiClient googleApiClient;
	private final GoogleConfig googleConfig;

	public GoogleLoginProvider(GoogleApiClient googleApiClient, GoogleConfig googleConfig) {
		this.googleApiClient = googleApiClient;
		this.googleConfig = googleConfig;
	}

	@Override
	public OauthServerType getSupportedServerType() {
		return OauthServerType.GOOGLE;
	}

	@Override
	public OauthMember getTokenAndMemberInfo(String authCode) {
		GoogleToken googleToken = googleApiClient.fetchToken(tokenRequestParams(authCode));
		log.debug("* google accessToken : {}", googleToken.accessToken());
		GoogleMemberResponseDto googleMemberResponse = googleApiClient.fetchMember(
				"Bearer " + googleToken.accessToken())
			.withAccessToken(googleToken.accessToken());

		return googleMemberResponse.toOauthMember(); // = toDomain()
	}

	@Override
	public OauthMember getMemberInfo(String accessToken) {
		GoogleMemberResponseDto googleMemberResponse = googleApiClient.fetchMember(
				"Bearer " + accessToken)
			.withAccessToken(accessToken);

		return googleMemberResponse.toOauthMember(); // = toDomain()
	}

	private MultiValueMap<String, String> tokenRequestParams(String authCode) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", googleConfig.clientId());
		params.add("client_secret", googleConfig.clientSecret());
		params.add("code", authCode);
		params.add("redirect_uri", googleConfig.redirectUri());
		return params;
	}
}
