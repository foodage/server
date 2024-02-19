package com.fourdays.foodage.oauth.service;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.member.exception.MemberInvalidStateException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.dto.OauthLoginResponseDto;
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

	public OauthService(OauthRequestUriProviderImpl requestUriProvider, OauthLoginProviderImpl oauthClient,
		MemberCommandService memberCommandService) {
		this.requestUriProvider = requestUriProvider;
		this.oauthClient = oauthClient;
		this.memberCommandService = memberCommandService;
	}

	public String getRequestUri(OauthServerType oauthServerType) {

		String requestUri = requestUriProvider.getRequestUri(oauthServerType);
		log.debug("request uri : {}", requestUri);
		return requestUri;
	}

	public OauthLoginResponseDto login(OauthServerType oauthServerType, String authCode) {

		// 매핑되는 server의 api로 token, 사용자 정보(member info) 요청
		OauthMember oauthMemberInfo = oauthClient.fetch(oauthServerType,
			authCode);

		// 해당 사용자 정보가 db에 존재하는지(기존 가입 여부) 확인
		try {
			memberCommandService.login(oauthMemberInfo.getOauthId(), oauthMemberInfo.getAccountEmail());
		} catch (MemberNotJoinedException e) { // 미가입 사용자
			log.debug(e.getMessage());
			return OauthLoginResponseDto.builder()
				.oauthId(oauthMemberInfo.getOauthId())
				.nickname(oauthMemberInfo.getNickname())
				.accountEmail(oauthMemberInfo.getAccountEmail())
				.result(LoginResult.NOT_JOINED)
				.build();
		} catch (MemberInvalidStateException e) { // 휴면, 블락 등의 상태를 가진 사용자
			log.debug(e.getMessage());
			return OauthLoginResponseDto.builder()
				.oauthId(oauthMemberInfo.getOauthId())
				.nickname(oauthMemberInfo.getNickname())
				.accountEmail(oauthMemberInfo.getAccountEmail())
				.result(e.getLoginResult())
				.build();
		}

		return OauthLoginResponseDto.builder()
			.oauthId(oauthMemberInfo.getOauthId())
			.nickname(oauthMemberInfo.getNickname())
			.accountEmail(oauthMemberInfo.getAccountEmail())
			.result(LoginResult.JOINED)
			.build();
	}
}