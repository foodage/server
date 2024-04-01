package com.fourdays.foodage.jwt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.JwtType;
import com.fourdays.foodage.jwt.handler.JwtFilter;

import lombok.extern.slf4j.Slf4j;

/*
 * Controller 레벨에서만 사용
 */
@Service
@Slf4j
public class AuthUtilService {

	private final AuthService authService;
	@Value("${application.domain}")
	private String domain;

	public AuthUtilService(AuthService authService) {
		this.authService = authService;
	}

	//////////////////// header ////////////////////
	public HttpHeaders createJwtHeader(String nickname, String accountEmail, boolean useCookie) {

		TokenDto jwt = authService.createToken(nickname, accountEmail);
		if (useCookie) {
			return createCookieHeader(jwt);
		}
		return createHeader(jwt);
	}

	public HttpHeaders createHeader(TokenDto jwt) {

		log.debug("# createHeader\n[header] accessToken : {}\n[header] refreshToken : {})",
			jwt.accessToken(), jwt.refreshToken());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, jwt.accessToken());
		httpHeaders.add(JwtType.REFRESH_TOKEN.getHeaderName(), jwt.refreshToken());
		return httpHeaders;
	}

	//////////////////// cookie ////////////////////
	private HttpHeaders createCookieHeader(TokenDto jwt) {

		log.debug("# createCookieHeader\n[set-cookie] accessToken : {}\n[set-cookie] refreshToken : {})",
			jwt.accessToken(), jwt.refreshToken());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Set-Cookie",
			createCookie(JwtFilter.AUTHORIZATION_HEADER, jwt.accessToken()));
		httpHeaders.add("Set-Cookie",
			createCookie(JwtType.REFRESH_TOKEN.getHeaderName(), jwt.refreshToken()));

		return httpHeaders;
	}

	public HttpHeaders createCookieHeader(String oauthAccessToken, String oauthServerType) {

		log.debug("# createCookieHeader\n[set-cookie] accessToken : {}\n[set-cookie] oauthServerName : {})",
			oauthAccessToken, oauthServerType);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Set-Cookie",
			createCookie("Oauth-Access-Token", oauthAccessToken));
		httpHeaders.add("Set-Cookie",
			createCookie("Oauth-Server-Type", oauthServerType));

		return httpHeaders;
	}

	////////////////////////////////////////////////////
	private String createCookie(String name, String value) {
		ResponseCookie cookie = ResponseCookie.from(name, value)
			.path("/")
			.domain(domain)
			.maxAge(300) // 5m
			.sameSite("Lax")
			// .secure(true)
			.build();

		return cookie.toString();
	}
}
