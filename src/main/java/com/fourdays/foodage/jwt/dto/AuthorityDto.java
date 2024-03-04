package com.fourdays.foodage.jwt.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorityDto {

	private String authorityName;

	@Builder
	public AuthorityDto(String authorityName) {
		this.authorityName = authorityName;
	}
}
