package com.fourdays.foodage.jwt.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

	@NotNull
	@Size(min = 3, max = 50)
	private String nickname;

	@NotNull
	@Size(min = 3, max = 100)
	private String email;

	@Builder
	public LoginDto(String nickname, String email) {
		this.nickname = nickname;
		this.email = email;
	}
}
