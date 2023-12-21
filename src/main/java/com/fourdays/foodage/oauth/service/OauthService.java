package com.fourdays.foodage.oauth.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.repository.MemberRepository;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.dto.response.OauthLoginResponse;
import com.fourdays.foodage.oauth.util.OauthLoginProviderImpl;
import com.fourdays.foodage.oauth.util.OauthRequestUriProviderImpl;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OauthService {

	private final OauthRequestUriProviderImpl requestUriProvider;
	private final OauthLoginProviderImpl oauthClient;
	private final MemberRepository memberRepository;

	public OauthService(OauthRequestUriProviderImpl requestUriProvider, OauthLoginProviderImpl oauthClient,
		MemberRepository memberRepository) {
		this.requestUriProvider = requestUriProvider;
		this.oauthClient = oauthClient;
		this.memberRepository = memberRepository;
	}

	public String getRequestUri(OauthServerType oauthServerType) {
		String requestUri = requestUriProvider.getRequestUri(oauthServerType);
		log.debug("request uri : {}", requestUri);
		return requestUri;
	}

	public OauthLoginResponse login(OauthServerType oauthServerType, String authCode) {
		// 매핑되는 server의 api로 token, 사용자 정보(member info) 요청
		OauthMember oauthMemberInfo = oauthClient.fetch(oauthServerType,
			authCode);

		// 해당 사용자 정보가 db에 존재하는지(기존 가입 여부) 확인
		Optional<Member> findMember = memberRepository.findByOauthIdAndAccountEmail(oauthMemberInfo.getOauthId(),
			oauthMemberInfo.getAccountEmail());
		if (!findMember.isPresent()) {
			return new OauthLoginResponse(oauthMemberInfo.getOauthId(), oauthMemberInfo.getAccountEmail(),
				LoginResult.FAILED);
		}

		// jwt 발급
		return new OauthLoginResponse(oauthMemberInfo.getOauthId(), findMember.get().getAccountEmail(),
			LoginResult.SUCCEEDED);
	}
}