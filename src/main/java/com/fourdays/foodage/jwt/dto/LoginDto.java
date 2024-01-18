package com.fourdays.foodage.jwt.dto;

import lombok.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {

	@NotNull
	@Size(min = 3, max = 50)
	private String username;

	@NotNull
	@Size(min = 3, max = 100)
	private String email;

	@Builder
	public LoginDto(String username, String email) {
		this.username = username;
		this.email = email;
	}
}
