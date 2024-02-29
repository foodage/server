package com.fourdays.foodage.jwt.service;

import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.jwt.domain.ExpiredToken;
import com.fourdays.foodage.jwt.domain.ExpiredTokenRepository;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.handler.TokenProvider;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.oauth.domain.OauthId;

@Service
public class AuthService {

	private final ExpiredTokenRepository expiredTokenRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;
	private final MemberQueryService memberQueryService;
	private final PasswordEncoder passwordEncoder;

	public AuthService(ExpiredTokenRepository expiredTokenRepository,
		AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider,
		MemberQueryService memberQueryService, PasswordEncoder passwordEncoder) {
		this.expiredTokenRepository = expiredTokenRepository;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.tokenProvider = tokenProvider;
		this.memberQueryService = memberQueryService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional(readOnly = true)
	public boolean isBlacklisted(String refreshToken) {

		boolean isExist = expiredTokenRepository.findByKey(refreshToken) != null;
		return isExist; // 존재하면 true
	}

	@Transactional
	public void addToBlacklist(String refreshToken) {

		ExpiredToken expiredToken = new ExpiredToken(refreshToken);
		expiredTokenRepository.save(expiredToken);
	}

	//////////////////////////////////////////////////////////////////

	public String createCredential() {
		return UUID.randomUUID().toString();
	}

	public String updateCredential(OauthId oauthId, String accountEmail) {

		Member member = memberQueryService.getMember(oauthId, accountEmail);
		String credential = createCredential();
		member.updateCredential(passwordEncoder.encode(credential));

		return credential;
	}

	// createToken 전, member credential 초기화 하는 과정 반드시 필요함 (updateCredential 메소드 사용)
	// 초기화 후 plain text를 두 번째 인자로 입력
	public TokenDto createToken(String nickname, String plainCredential) {

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(nickname, plainCredential);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenDto jwt = tokenProvider.createToken(authentication);
		return jwt;
	}
}
