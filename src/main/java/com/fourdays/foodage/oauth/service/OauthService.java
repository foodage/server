package com.fourdays.foodage.oauth.service;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.JwtType;
import com.fourdays.foodage.jwt.handler.JwtFilter;
import com.fourdays.foodage.jwt.handler.TokenProvider;
import com.fourdays.foodage.member.domain.MemberRepository;
import com.fourdays.foodage.member.exception.MemberInvalidStateException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.oauth.domain.OauthId;
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
	private final AuthenticationManagerBuilder authenticationManagerBuilder; // 서비스 분리
	private final TokenProvider tokenProvider; // 서비스 분리
	private final MemberCommandService memberCommandService;
	private final MemberRepository memberRepository;

	public OauthService(OauthRequestUriProviderImpl requestUriProvider, OauthLoginProviderImpl oauthClient,
		AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider,
		MemberCommandService memberCommandService, MemberRepository memberRepository) {
		this.requestUriProvider = requestUriProvider;
		this.oauthClient = oauthClient;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.tokenProvider = tokenProvider;
		this.memberCommandService = memberCommandService;
		this.memberRepository = memberRepository;
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
			memberCommandService.login(oauthMemberInfo.getOauthId(),
				oauthMemberInfo.getAccountEmail());
		} catch (MemberNotJoinedException e) { // 미가입 사용자
			log.debug(e.getMessage());
			return new OauthLoginResponseDto(oauthMemberInfo.getOauthId(), oauthMemberInfo.getAccountEmail(),
				LoginResult.NOT_JOINED);
		} catch (MemberInvalidStateException e) { // 휴면, 블락 등의 상태를 가진 사용자
			log.debug(e.getMessage());
			return new OauthLoginResponseDto(oauthMemberInfo.getOauthId(), oauthMemberInfo.getAccountEmail(),
				e.getLoginResult());
		}
		return new OauthLoginResponseDto(oauthMemberInfo.getOauthId(), oauthMemberInfo.getAccountEmail(),
			LoginResult.JOINED);
	}

	public HttpHeaders provideToken(OauthId oauthId, String accountEmail) {
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(oauthId, accountEmail);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenDto jwt = tokenProvider.createToken(authentication);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt.accessToken());
		httpHeaders.add(JwtType.REFRESH_TOKEN.getHeaderName(), "Bearer " + jwt.refreshToken());

		return httpHeaders;
	}
}