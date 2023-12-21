package com.fourdays.foodage.oauth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.oauth.dto.response.OauthLoginResponse;
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

	@Operation(summary = "Oauth 서비스 연동 URI 조회")
	@GetMapping("/oauth/{oauthServerType}")
	ResponseEntity<String> getRequestUrl(@PathVariable OauthServerType oauthServerType
		// HttpServletResponse response
	) {

		String redirectUrl = oauthService.getRequestUri(oauthServerType);
		// response.sendRedirect(redirectUrl);
		return ResponseEntity.ok().body(redirectUrl);
	}

	// oauth -> (backend) redirect url로 전달하는 auth code를 receive할 api
	@Operation(hidden = true)
	@GetMapping("/oauth/{oauthServerName}/auth-code")
	ResponseEntity<OauthLoginResponse> receiveAuthCode(@PathVariable String oauthServerName,
		@RequestParam String code) {

		log.debug("received auth code : {}", code);

		OauthServerType oauthServerType = OauthServerType.fromName(oauthServerName);
		OauthLoginResponse result = oauthService.login(oauthServerType, code);

		return ResponseEntity.ok().body(result);
	}
}