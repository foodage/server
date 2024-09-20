package com.fourdays.foodage.oauth.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.dto.ResponseDto;
import com.fourdays.foodage.jwt.service.AuthUtilService;
import com.fourdays.foodage.member.dto.MemberLoginResultDto;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.service.OauthService;
import com.fourdays.foodage.oauth.util.OauthServerType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Oauth API")
public class OauthController {

	private final OauthService oauthService;
	private final MemberCommandService memberCommandService;
	private final AuthUtilService authUtilService;

	@Value("${application.client.base-url}")
	private String clientBaseUrl;

	public OauthController(OauthService oauthService, MemberCommandService memberCommandService,
		AuthUtilService authUtilService) {
		this.oauthService = oauthService;
		this.memberCommandService = memberCommandService;
		this.authUtilService = authUtilService;
	}

	@Operation(summary = "oauth 서비스 연동 url 조회 (테스트용)", hidden = true)
	@GetMapping("/oauth/{oauthServerType}")
	public ResponseEntity<ResponseDto<String>> getRequestUrl(
		@PathVariable OauthServerType oauthServerType
	) {
		String redirectUrl = oauthService.getRequestUri(oauthServerType);
		return ResponseEntity.ok()
			.body(ResponseDto.success(redirectUrl));
	}

	@Operation(summary = "redirect url에 대한 api. auth code receive 후 로그인 절차 수행", hidden = true)
	@GetMapping("/oauth/{oauthServerName}/login")
	public ResponseEntity login(@PathVariable(value = "oauthServerName") String oauthServerName,
		@RequestParam String code) {

		log.debug("* received auth code : {}", code);

		OauthServerType oauthServerType = OauthServerType.fromName(oauthServerName);
		OauthMember oauthMember = oauthService.getOauthMember(oauthServerType, code);

		MemberLoginResultDto loginResult = memberCommandService.login(oauthMember.getOauthId(),
			oauthMember.getAccountEmail());

		HttpHeaders httpHeaders = new HttpHeaders();
		String redirectUrl = "";
		switch (loginResult.loginResult()) {
			case SUCCESS -> { // 이미 가입된 상태. 정상 로그인 처리
				httpHeaders = authUtilService.createJwtHeader(loginResult.oauthId().getOauthServerType(),
					loginResult.accountEmail(), loginResult.credential(), true);
				redirectUrl = clientBaseUrl + "/";
			}
			case JOIN_IN_PROGRESS -> { // 가입 진행중인 상태로 로그인 불가. 회원가입을 위한 추가 정보 입력 요청
				httpHeaders = authUtilService.createCookieHeader(oauthMember.getAccessToken(),
					oauthServerType.name().toLowerCase());
				redirectUrl = clientBaseUrl + "/signup";
			}
			case LEAVE_IN_PROGRESS -> { // 탈퇴 후 30일이 지나지 않아 계정 복구가 가능한 상태
				httpHeaders = authUtilService.createCookieHeader(oauthMember.getAccessToken(),
					oauthServerType.name().toLowerCase());
				redirectUrl = clientBaseUrl + "/restore";
			}
			case DORMANT_MEMBER -> { // 휴면 상태. 휴면 해지를 위한 페이지로 이동
				httpHeaders = authUtilService.createCookieHeader(oauthMember.getAccessToken(),
					oauthServerType.name().toLowerCase());
				redirectUrl = clientBaseUrl + "/dormant";
			}
		}
		httpHeaders.add(HttpHeaders.LOCATION, redirectUrl);

		return ResponseEntity
			.status(HttpStatus.TEMPORARY_REDIRECT)
			.headers(httpHeaders)
			.build();
	}
}
