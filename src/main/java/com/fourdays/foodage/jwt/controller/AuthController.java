package com.fourdays.foodage.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.dto.ResponseDto;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.jwt.dto.ReissueTokenRequestDto;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.exception.BlockedRefreshTokenException;
import com.fourdays.foodage.jwt.handler.TokenProvider;
import com.fourdays.foodage.jwt.service.AuthService;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "authentication (jwt)", description = "jwt 재발행과 같은 인증 처리 api")
public class AuthController {

	private final TokenProvider tokenProvider;
	private final AuthService authService;
	private final MemberQueryService memberQueryService;

	public AuthController(TokenProvider tokenProvider, AuthService authService, MemberQueryService memberQueryService) {
		this.tokenProvider = tokenProvider;
		this.authService = authService;
		this.memberQueryService = memberQueryService;
	}

	// 개발 테스트용 api
	@Operation(summary = "jwt 발급", hidden = true)
	@PostMapping("/jwt/test-issue")
	public ResponseEntity<ResponseDto<TokenDto>> issueToken(
		@RequestParam("oauthServerName") String oauthServerName,
		@RequestParam("accountEmail") String accountEmail) {

		// 신규 토큰 발급
		OauthServerType oauthServerType = OauthServerType.from(oauthServerName);
		Member findMember = memberQueryService.findByMemberId(MemberId.create(oauthServerType, accountEmail));
		String credential = authService.updateCredential(findMember.getOauthId(), accountEmail);
		TokenDto reissueJwt = authService.createToken(findMember.getOauthId().getOauthServerType(),
			findMember.getAccountEmail(), credential);

		return ResponseEntity.ok()
			.body(ResponseDto.success(reissueJwt));
	}

	@Operation(summary = "jwt 재발급")
	@PostMapping("/jwt/reissue")
	public ResponseEntity<ResponseDto<TokenDto>> reissueToken(
		@RequestBody ReissueTokenRequestDto reissueTokenRequest) {

		String refreshToken = reissueTokenRequest.refreshToken();

		// 만료된 refresh token인지 확인 (만료된 rt는 blacklist 테이블에 추가됨)
		tokenProvider.validateToken(refreshToken);
		if (authService.isBlacklisted(refreshToken)) {
			throw new BlockedRefreshTokenException(ExceptionInfo.ERR_BLOCKED_REFRESH_TOKEN);
		}

		// 신규 토큰 발급
		Member findMember = memberQueryService.findByMemberId(MemberId.create(
			reissueTokenRequest.oauthServerType(), reissueTokenRequest.accountEmail()));
		String credential = authService.updateCredential(findMember.getOauthId(), reissueTokenRequest.accountEmail());
		TokenDto reissueJwt = authService.createToken(findMember.getOauthId().getOauthServerType(),
			findMember.getAccountEmail(), credential);

		// 기존 refresh token은 redis 만료 테이블에 추가
		authService.addToBlacklist(refreshToken);

		return ResponseEntity.ok()
			.body(ResponseDto.success(reissueJwt));
	}
}
