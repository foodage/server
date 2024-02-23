package com.fourdays.foodage.oauth.dto;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.oauth.domain.OauthId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OauthLoginResponseDto {

	@Schema(description = "간편 로그인 종류")
	private OauthId oauthId;

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "간편 로그인한 계정의 ID (email)")
	private String accountEmail;

	@Schema(description = "로그인 결과", example = "SUCCEEDED, FAILED")
	private LoginResult result;
}
