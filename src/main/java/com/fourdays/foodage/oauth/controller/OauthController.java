package com.fourdays.foodage.oauth.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.oauth.dto.OauthLoginResponseDto;
import com.fourdays.foodage.oauth.service.OauthService;
import com.fourdays.foodage.oauth.util.OauthServerType;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OauthController implements OauthApi {

	private final OauthService oauthService;

	public OauthController(OauthService oauthService) {
		this.oauthService = oauthService;
	}

	@Override // @GetMapping("/oauth/{oauthServerType}")
	public ResponseEntity<String> getRequestUrl(@PathVariable OauthServerType oauthServerType
		// HttpServletResponse response
	) {

		String redirectUrl = oauthService.getRequestUri(oauthServerType);
		// response.sendRedirect(redirectUrl);
		return ResponseEntity.ok().body(redirectUrl);
	}

	@Override // @GetMapping("/oauth/{oauthServerName}/login")
	public ResponseEntity<OauthLoginResponseDto> login(@PathVariable String oauthServerName,
		@RequestParam String code, HttpServletResponse response) throws IOException {

		log.debug("received auth code : {}", code);

		OauthServerType oauthServerType = OauthServerType.fromName(oauthServerName);
		OauthLoginResponseDto result = oauthService.login(oauthServerType, code);

		log.debug("{} : {}", result.getResult().name(), result.getResult().getDetailMessage());

		HttpHeaders httpHeaders = null;
		if (result.getResult().equals(LoginResult.JOINED)) {
			httpHeaders = oauthService.provideToken(result.getOauthId(), result.getAccountEmail());
		}

		// 회원가입 페이지로 이동시킴
		// String redirectUrl = "http://localhost:3000/tutorial";
		// response.sendRedirect(redirectUrl);
		return ResponseEntity.ok().headers(httpHeaders).body(result);
	}
}