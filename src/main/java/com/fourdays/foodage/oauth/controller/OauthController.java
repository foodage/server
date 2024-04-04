package com.fourdays.foodage.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.jwt.service.AuthUtilService;
import com.fourdays.foodage.oauth.dto.OauthLoginResponseDto;
import com.fourdays.foodage.oauth.service.OauthService;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Oauth API")
public class OauthController {

	private final OauthService oauthService;
	private final AuthUtilService authUtilService;

	@Value("${application.client.base-url}")
	private String clientBaseUrl;

	public OauthController(OauthService oauthService, AuthUtilService authUtilService) {
		this.oauthService = oauthService;
		this.authUtilService = authUtilService;
	}

	@Operation(summary = "oauth 서비스 연동 url 조회 (테스트용)", hidden = true)
	@GetMapping("/oauth/{oauthServerType}")
	public ResponseEntity<String> getRequestUrl(@PathVariable OauthServerType oauthServerType
	) {

		String redirectUrl = oauthService.getRequestUri(oauthServerType);
		return ResponseEntity.ok().body(redirectUrl);
	}

	@Operation(summary = "redirect url에 대한 api. auth code receive 후 로그인 절차 수행", hidden = true)
	@GetMapping("/oauth/{oauthServerName}/login")
	public ResponseEntity login(@PathVariable String oauthServerName, @RequestParam String code,
		HttpServletResponse response) {

		log.debug("received auth code : {}", code);

		OauthServerType oauthServerType = OauthServerType.fromName(oauthServerName);
		OauthLoginResponseDto loginResult = oauthService.login(oauthServerType, code); // foodage 서비스 가입자인지 확인

		log.debug("{} : {}", loginResult.result().name(),
			loginResult.result().getDetailMessage());

		HttpHeaders httpHeaders = new HttpHeaders();
		String redirectUrl = "";
		if (loginResult.result() == LoginResult.JOINED) {

			httpHeaders = authUtilService.createJwtHeader(loginResult.oauthServerType(),
				loginResult.accountEmail(), loginResult.credential(), true); // header 토큰 받을 수 있는지 확인 필요
			redirectUrl = clientBaseUrl + "/home";
		}
		if (loginResult.result() == LoginResult.NOT_JOINED
			|| loginResult.result() == LoginResult.JOIN_IN_PROGRESS) {

			httpHeaders = authUtilService.createCookieHeader(loginResult.accessToken(), oauthServerName.toLowerCase());
			redirectUrl = clientBaseUrl + "/signup";
		}
		httpHeaders.add(HttpHeaders.LOCATION, redirectUrl);

		return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
			.headers(httpHeaders).build();
	}
}
