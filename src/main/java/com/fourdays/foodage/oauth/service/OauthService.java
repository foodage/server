package com.fourdays.foodage.oauth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.domain.repository.OauthMemberRepository;
import com.fourdays.foodage.oauth.util.OauthRequestUriProviderImpl;
import com.fourdays.foodage.oauth.util.OauthServerType;
import com.fourdays.foodage.oauth.util.OauthTokenProviderImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OauthService {

	private final OauthRequestUriProviderImpl requestUriProvider;
	private final OauthTokenProviderImpl oauthClient;
	private final OauthMemberRepository oauthMemberRepository;

	public OauthService(OauthRequestUriProviderImpl requestUriProvider,
		OauthTokenProviderImpl oauthClient, OauthMemberRepository oauthMemberRepository) {
		this.requestUriProvider = requestUriProvider;
		this.oauthClient = oauthClient;
		this.oauthMemberRepository = oauthMemberRepository;
	}

	public String getRequestUri(OauthServerType oauthServerType) {
		String requestUri = requestUriProvider.getRequestUri(oauthServerType);
		log.debug("request uri : {}", requestUri);
		return requestUri;
	}

	public Long login(OauthServerType oauthServerType, String authCode) {
		OauthMember oauthMember = oauthClient.fetch(oauthServerType, authCode);
		Optional<OauthMember> findMember = oauthMemberRepository.findByOauthId(oauthMember.oauthId());

		if (findMember.isPresent()) {
			// 추가 정보 필요한걸 알려야함, 회원가입 api 받아서 이후 사용자 데이터 입력.
			oauthMemberRepository.save(oauthMember);
		}

		return findMember.get().id();
	}
}