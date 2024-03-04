package com.fourdays.foodage.oauth.util;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.exception.OauthException;

@Component
public class OauthLoginProviderImpl {

	private final Map<OauthServerType, OauthLoginProvider> oauthInfo;

	public OauthLoginProviderImpl(Set<OauthLoginProvider> clients) {
		oauthInfo = clients.stream()
			.collect(toMap(
				OauthLoginProvider::getSupportedServerType,
				identity()
			));
	}

	public OauthMember fetch(OauthServerType oauthServerType, String authCode) {
		return getClient(oauthServerType).getTokenAndMemberInfo(authCode);
	}

	public OauthMember fetchMember(OauthServerType oauthServerType, String accessToken) {
		return getClient(oauthServerType).getMemberInfo(accessToken);
	}

	private OauthLoginProvider getClient(OauthServerType oauthServerType) {
		return Optional.ofNullable(oauthInfo.get(oauthServerType))
			.orElseThrow(() -> new OauthException(ResultCode.ERR_NOT_SUPPORTED_OAUTH_SERVER_TYPE));
	}
}