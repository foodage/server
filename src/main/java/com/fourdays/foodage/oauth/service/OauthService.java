package com.fourdays.foodage.oauth.service;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.member.dto.MemberLoginInfoDto;
import com.fourdays.foodage.member.exception.MemberInvalidStateException;
import com.fourdays.foodage.member.exception.MemberJoinInProgressException;
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
		OauthMember oauthMemberInfo = oauthClient.fetch(oauthServerType, authCode);

		// 해당 사용자 정보가 db에 존재하는지(기존 가입 여부) 확인
		LoginResult loginResult;
		MemberLoginInfoDto memberLoginInfo = null;
		try {
			memberLoginInfo = memberCommandService.login(oauthMemberInfo.getOauthId(),
				oauthMemberInfo.getAccountEmail());
			loginResult = memberLoginInfo.loginResult();

		} catch (MemberNotJoinedException e) { // 미가입 사용자
			// redirect시 사용자 정보를 body에 전달할 수 없어, 사용자 데이터 임시 저장한 후 update하는 방법으로 회원가입 처리
			memberLoginInfo = memberCommandService.tempJoin(oauthMemberInfo.getOauthId(),
				oauthMemberInfo.getAccountEmail());
			log.debug(e.getMessage());
			loginResult = memberLoginInfo.loginResult();

		} catch (MemberJoinInProgressException e) { // 가입 진행중인 사용자
			log.debug(e.getMessage());
			loginResult = e.getLoginResult(); // BLOCK or LEAVE state

		} catch (MemberInvalidStateException e) { // 서비스 접근 불가능 상태인 사용자
			log.debug(e.getMessage());
			loginResult = e.getLoginResult(); // BLOCK or LEAVE state
		}

		return OauthLoginResponseDto.builder()
			.memberId(memberLoginInfo.memberId())
			.accountEmail(oauthMemberInfo.getAccountEmail())
			.nickname(memberLoginInfo.nickname())
			.result(loginResult)
			.build();
	}
}