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

	public String updateCredential(String accountEmail) {

		Member member = memberQueryService.getMemberByAccountEmail(accountEmail);
		String credential = createCredential();
		member.updateCredential(passwordEncoder.encode(credential));

		return credential;
	}

	public TokenDto createToken(String nickname, String plainCredential) {

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(nickname, plainCredential);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenDto jwt = tokenProvider.createToken(authentication);
		return jwt;
	}
}
