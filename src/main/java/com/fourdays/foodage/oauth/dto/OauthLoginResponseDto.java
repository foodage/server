package com.fourdays.foodage.oauth.dto;

import com.fourdays.foodage.common.enums.LoginResult;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OauthLoginResponseDto {

	@Schema(description = "닉네임")
	private String nickname;

	@Schema(description = "간편 로그인한 계정의 ID (email)")
	private String accountEmail;
	
	@Schema(description = "회원가입 진행중인 사용자의 ID")
	private Long memberId;

	@Schema(description = "로그인 결과", example = "SUCCEEDED, FAILED")
	private LoginResult result;
}
