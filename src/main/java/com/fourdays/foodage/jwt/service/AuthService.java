package com.fourdays.foodage.jwt.service;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.jwt.domain.ExpiredToken;
import com.fourdays.foodage.jwt.domain.ExpiredTokenRepository;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.JwtType;
import com.fourdays.foodage.jwt.handler.JwtFilter;
import com.fourdays.foodage.jwt.handler.TokenProvider;

@Service
public class AuthService {

	private final ExpiredTokenRepository expiredTokenRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;

	public AuthService(ExpiredTokenRepository expiredTokenRepository,
		AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider) {
		this.expiredTokenRepository = expiredTokenRepository;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.tokenProvider = tokenProvider;
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

	public TokenDto createToken(String nickname, String plainCredential) {

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(nickname, plainCredential);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenDto jwt = tokenProvider.createToken(authentication);
		return jwt;
	}

	public HttpHeaders createTokenHeader(String nickname, String accountEmail) {

		TokenDto jwt = createToken(nickname, accountEmail);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt.accessToken());
		httpHeaders.add(JwtType.REFRESH_TOKEN.getHeaderName(), "Bearer " + jwt.refreshToken());

		return httpHeaders;
	}
}
