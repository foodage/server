package com.fourdays.foodage.oauth.util;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fourdays.foodage.oauth.domain.OauthMember;

@Component
public class OauthTokenProviderImpl {

	private final Map<OauthServerType, OauthTokenProvider> oauthInfo;

	public OauthTokenProviderImpl(Set<OauthTokenProvider> clients) {
		oauthInfo = clients.stream()
			.collect(toMap(
				OauthTokenProvider::getSupportedServerType,
				identity()
			));
	}

	public OauthMember fetch(OauthServerType oauthServerType, String authCode) {
		return getClient(oauthServerType).getOAuthToken(authCode);
	}

	private OauthTokenProvider getClient(OauthServerType oauthServerType) {
		return Optional.ofNullable(oauthInfo.get(oauthServerType))
			.orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 종류입니다."));
	}
}