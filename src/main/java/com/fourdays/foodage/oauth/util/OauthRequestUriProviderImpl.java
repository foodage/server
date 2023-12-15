package com.fourdays.foodage.oauth.util;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class OauthRequestUriProviderImpl {

	private final Map<OauthServerType, OauthRequestUriProvider> oauthInfo;

	public OauthRequestUriProviderImpl(Set<OauthRequestUriProvider> providers) {
		oauthInfo = providers.stream()
			.collect(toMap(
				OauthRequestUriProvider::getSupportedServerType,
				identity()
			));
	}

	public String getRequestUri(OauthServerType oauthServerType) {
		return getServerTypeProvider(oauthServerType).buildRequestUri(); // 요청한 서버의 provider 구현체를 가져옴
	}

	private OauthRequestUriProvider getServerTypeProvider(OauthServerType oauthServerType) {
		return Optional.ofNullable(oauthInfo.get(oauthServerType))
			.orElseThrow(() -> new RuntimeException("지원하지 않는 소셜 로그인 종류입니다."));
	}
}