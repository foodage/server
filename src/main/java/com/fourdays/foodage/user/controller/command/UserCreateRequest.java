package com.fourdays.foodage.user.controller.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCreateRequest {

	@NotBlank(message = "닉네임을 입력해주세요.")
	@Size(min = 2, max = 8, message = "닉네임은 2~8글자 사이로 입력해주세요. (예: 푸디지)")
	private String nickname;

	private String profileUrl;

	public UserCreateRequest(String nickname, String profileUrl) {
		this.nickname = nickname;
		this.profileUrl = profileUrl;
	}
}
