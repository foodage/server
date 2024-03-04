package com.fourdays.foodage.jwt.service;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.JwtType;
import com.fourdays.foodage.jwt.handler.JwtFilter;

/*
 * Controller 레벨에서만 사용
 */
@Service
public class AuthUtilService {

	private final AuthService authService;

	public AuthUtilService(AuthService authService) {
		this.authService = authService;
	}

	public HttpHeaders createTokenHeader(String nickname, String accountEmail) {

		TokenDto jwt = authService.createToken(nickname, accountEmail);
		return createTokenHeader(jwt);
	}

	public HttpHeaders createTokenHeader(TokenDto jwt) {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt.accessToken());
		httpHeaders.add(JwtType.REFRESH_TOKEN.getHeaderName(), jwt.refreshToken());
		return httpHeaders;
	}
}
