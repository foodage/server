package com.fourdays.foodage.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.oauth.service.OauthService;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OauthController {

	private final OauthService oauthService;

	public OauthController(OauthService oauthService) {
		this.oauthService = oauthService;
	}

	@Operation(summary = "OAuth 서비스 연동 URI 조회")
	@GetMapping("/oauth/{oauthServerType}")
	ResponseEntity<String> getRequestUrl(@PathVariable OauthServerType oauthServerType
		// HttpServletResponse response
	) {

		String redirectUrl = oauthService.getRequestUri(oauthServerType);
		// response.sendRedirect(redirectUrl);
		return ResponseEntity.ok().body(redirectUrl);
	}

	// oauth server -> (backend) redirect url로 auth code를 파라미터에 담아 request
	// 위 로직을 처리하기 위한 api
	@Operation(hidden = true)
	@GetMapping("/oauth/{oauthServerType}/auth-code")
	ResponseEntity<String> receiveAuthCode(@RequestParam String code) {

		log.debug("received auth code : {}", code);
		return ResponseEntity.ok().body(code);
	}

	@Operation(summary = "로그인")
	@GetMapping("/login/{oauthServerType}")
	ResponseEntity<Long> login(
		@PathVariable OauthServerType oauthServerType,
		@RequestParam("code") String code
	) {
		Long login = oauthService.login(oauthServerType, code);
		return ResponseEntity.ok(login);
	}
}