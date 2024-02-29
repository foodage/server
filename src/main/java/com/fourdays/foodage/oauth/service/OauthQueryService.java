package com.fourdays.foodage.oauth.service;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.util.OauthLoginProviderImpl;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OauthQueryService {

	private final OauthLoginProviderImpl oauthClient;

	public OauthQueryService(OauthLoginProviderImpl oauthClient) {
		this.oauthClient = oauthClient;
	}

	public OauthMember getOauthMember(String oauthServerName, String accessToken) {

		OauthServerType oauthServerType = OauthServerType.fromName(oauthServerName);
		return oauthClient.fetchMember(oauthServerType, accessToken);
	}
}
