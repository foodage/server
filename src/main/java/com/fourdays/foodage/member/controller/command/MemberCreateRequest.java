package com.fourdays.foodage.member.controller.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCreateRequest {

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, max = 8, message = "닉네임은 2~8글자 사이로 입력해주세요. (예: 푸디지)")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]+$", message = "닉네임에 사용할 수 없는 문자가 포함되어 있습니다. 다시 입력해주세요.")
	private String nickname;

	private String profileUrl;

	public MemberCreateRequest(String nickname, String profileUrl) {
		this.nickname = nickname;
		this.profileUrl = profileUrl;
	}
}
