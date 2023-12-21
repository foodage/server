package com.fourdays.foodage.oauth.dto.response;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.oauth.domain.OauthId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OauthLoginResponse {

	@Schema(description = "간편 로그인 종류")
	private OauthId oauthId;

	@Schema(description = "간편 로그인한 계정의 ID (email)")
	private String accountEmail;

	@Schema(description = "로그인 결과", example = "SUCCEEDED, FAILED")
	private LoginResult result;
}
