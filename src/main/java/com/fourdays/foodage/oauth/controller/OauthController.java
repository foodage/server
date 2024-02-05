package com.fourdays.foodage.oauth.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.oauth.dto.OauthLoginResponseDto;
import com.fourdays.foodage.oauth.service.OauthService;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OauthController {

	private final OauthService oauthService;

	public OauthController(OauthService oauthService) {
		this.oauthService = oauthService;
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
		String redirectUrl = "http://localhost:3000/tutorial";
		// response.sendRedirect(redirectUrl);
		return ResponseEntity.ok().headers(httpHeaders).body(result);
	}
}