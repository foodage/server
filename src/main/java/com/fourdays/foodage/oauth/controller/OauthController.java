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
import com.fourdays.foodage.jwt.service.AuthService;
import com.fourdays.foodage.oauth.dto.OauthLoginResponseDto;
import com.fourdays.foodage.oauth.service.OauthService;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OauthController {

	private final OauthService oauthService;
	private final AuthService authService;
	@Value("${application.client.base-url}")
	private String clientBaseUrl;

	public OauthController(OauthService oauthService, AuthService authService) {
		this.oauthService = oauthService;
		this.authService = authService;
	}

	@Operation(summary = "Oauth 서비스 연동 URI 조회")
	@GetMapping("/oauth/{oauthServerType}")
	public ResponseEntity<String> getRequestUrl(@PathVariable OauthServerType oauthServerType
	) {

		String redirectUrl = oauthService.getRequestUri(oauthServerType);
		return ResponseEntity.ok().body(redirectUrl);
	}

	// oauth -> (backend) redirect url로 전달하는 auth code를 receive & 사용자 정보 받아 login
	@Operation(hidden = true)
	@GetMapping("/oauth/{oauthServerName}/login")
	public ResponseEntity login(@PathVariable String oauthServerName, @RequestParam String code) {

		log.debug("received auth code : {}", code);

		OauthServerType oauthServerType = OauthServerType.fromName(oauthServerName);
		OauthLoginResponseDto result = oauthService.login(oauthServerType, code); // foodage 서비스 가입자인지 확인

		log.debug("{} : {}", result.getResult().name(), result.getResult().getDetailMessage());

		HttpHeaders httpHeaders = new HttpHeaders();
		String redirectUrl = "";
		if (result.getResult() == LoginResult.JOINED) {
			String credential = authService.updateCredential(result.getAccountEmail());
			httpHeaders = authService.createTokenHeader(result.getNickname(), credential);
			redirectUrl = clientBaseUrl + "/home";
		}
		if (result.getResult() == LoginResult.NOT_JOINED
			|| result.getResult() == LoginResult.JOIN_IN_PROGRESS) {
			redirectUrl = clientBaseUrl + "/signup/" + result.getMemberId();
		}
		httpHeaders.add(HttpHeaders.LOCATION, redirectUrl);

		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
			.headers(httpHeaders).build();
	}
}