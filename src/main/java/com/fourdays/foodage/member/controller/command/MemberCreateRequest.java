package com.fourdays.foodage.member.controller.command;

import com.fourdays.foodage.oauth.domain.OauthId;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateRequest {

	@NotBlank(message = "간편 로그인 서비스 종류를 입력해주세요.")
	private OauthId oauthId;

	@NotBlank(message = "계정 이메일을 입력해주세요.")
	private String accountEmail;

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, max = 8, message = "닉네임은 2~8글자 사이로 입력해주세요. (예: 푸디지)")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임에 사용할 수 없는 문자가 포함되어 있습니다. 다시 입력해주세요.")
	private String nickname;

	private String profileUrl = "default";

	public MemberCreateRequest(OauthId oauthId, String accountEmail, String nickname) {
		this.oauthId = oauthId;
		this.accountEmail = accountEmail;
		this.nickname = nickname;
	}

	public MemberCreateRequest(OauthId oauthId, String accountEmail, String nickname, String profileUrl) {
		this.oauthId = oauthId;
		this.accountEmail = accountEmail;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
	}
}
