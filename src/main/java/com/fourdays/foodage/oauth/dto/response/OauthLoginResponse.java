package com.fourdays.foodage.oauth.dto.response;

import com.fourdays.foodage.common.enums.LoginResult;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OauthLoginResponse {

	@Schema(description = "간편 로그인한 계정의 ID (email)")
	private String accountEmail;

	@Schema(description = "로그인 결과", example = "SUCCEEDED, FAILED")
	private LoginResult result;
}
