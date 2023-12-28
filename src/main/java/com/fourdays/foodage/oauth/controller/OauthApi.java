package com.fourdays.foodage.oauth.controller;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.fourdays.foodage.oauth.dto.OauthLoginResponseDto;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

/**
 * author         : 이름 (ex. ebkim) <br/>
 * date           : 23-12-25 <br/>
 * description    : 설명  <br/>
 */
public interface OauthApi {

	@Operation(summary = "Oauth 서비스 연동 URI 조회")
	@GetMapping("/oauth/{oauthServerType}")
	ResponseEntity<String> getRequestUrl(@PathVariable OauthServerType oauthServerType);

	// oauth -> (backend) redirect url로 전달하는 auth code를 receive & 사용자 정보 받아 login
	@Operation(hidden = true)
	@GetMapping("/oauth/{oauthServerName}/login")
	ResponseEntity<OauthLoginResponseDto> login(@PathVariable String oauthServerName,
		@RequestParam String code, HttpServletResponse response) throws IOException;
}
