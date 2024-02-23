package com.fourdays.foodage.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.jwt.dto.ReissueTokenRequestDto;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.exception.BlockedRefreshTokenException;
import com.fourdays.foodage.jwt.handler.TokenProvider;
import com.fourdays.foodage.jwt.service.AuthService;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/jwt")
@Slf4j
public class AuthController {

	private final TokenProvider tokenProvider;
	private final AuthService authService;
	private final MemberQueryService memberQueryService;

	public AuthController(TokenProvider tokenProvider, AuthService authService, MemberQueryService memberQueryService) {
		this.tokenProvider = tokenProvider;
		this.authService = authService;
		this.memberQueryService = memberQueryService;
	}

	@Operation(summary = "jwt 재발급")
	@PostMapping("/reissue")
	public ResponseEntity<TokenDto> reissueToken(
		@RequestBody ReissueTokenRequestDto reissueTokenRequest) {

		String refreshToken = reissueTokenRequest.refreshToken();

		// 만료된 refresh token인지 확인 (만료된 rt는 blacklist 테이블에 추가됨)
		tokenProvider.validateToken(refreshToken);
		if (authService.isBlacklisted(refreshToken)) {
			throw new BlockedRefreshTokenException(ResultCode.ERR_BLOCKED_REFRESH_TOKEN);
		}

		// 신규 토큰 발급
		Member findMember = memberQueryService.getMemberByAccountEmail(reissueTokenRequest.accountEmail());
		String credential = authService.updateCredential(reissueTokenRequest.accountEmail());
		TokenDto reissueJwt = authService.createToken(findMember.getNickname(), credential);

		// 기존 refresh token은 만료 테이블에 추가
		authService.addToBlacklist(refreshToken);

		return ResponseEntity.ok(reissueJwt);
	}
}
