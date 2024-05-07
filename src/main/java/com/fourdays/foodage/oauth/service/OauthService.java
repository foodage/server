package com.fourdays.foodage.oauth.service;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.jwt.service.AuthService;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.util.OauthLoginProviderImpl;
import com.fourdays.foodage.oauth.util.OauthRequestUriProviderImpl;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OauthService {

	private final OauthRequestUriProviderImpl requestUriProvider;
	private final OauthLoginProviderImpl oauthClient;
	private final MemberCommandService memberCommandService;
	private final AuthService authService;

	public OauthService(OauthRequestUriProviderImpl requestUriProvider, OauthLoginProviderImpl oauthClient,
		MemberCommandService memberCommandService, AuthService authService) {
		this.requestUriProvider = requestUriProvider;
		this.oauthClient = oauthClient;
		this.memberCommandService = memberCommandService;
		this.authService = authService;
	}

	public String getRequestUri(OauthServerType oauthServerType) {

		String requestUri = requestUriProvider.getRequestUri(oauthServerType);
		log.debug("# request uri : {}", requestUri);
		return requestUri;
	}

	public OauthMember getOauthMember(OauthServerType oauthServerType, String authCode) {

		// oauth 서버에 oauth access token, 사용자 정보(member info) 요청
		OauthMember oauthMember = oauthClient.fetch(oauthServerType, authCode);
		log.debug("# oauth access token : {}", oauthMember.getAccessToken());
		log.debug("# oauthServerId : {}\noauthServerType : {}\naccountEmail : {}",
			oauthMember.getOauthId().getOauthServerId(),
			oauthMember.getOauthId().getOauthServerType(),
			oauthMember.getAccountEmail());
		return oauthMember;
	}
}
