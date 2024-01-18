package com.fourdays.foodage.jwt.dto;

import java.util.Set;

import com.fourdays.foodage.jwt.domain.UserToken;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenDto {

	@NotNull
	@Size(min = 3, max = 50)
	private String username;

	@NotNull
	@Size(min = 3, max = 100)
	private String email;

	// private Set<AuthorityDto> authorityDtoSet;

	@Builder
	public UserTokenDto(String username, String email, Set<AuthorityDto> authorityDtoSet) {
		this.username = username;
		this.email = email;
		// this.authorityDtoSet = authorityDtoSet;
	}

	public static UserTokenDto from(UserToken userToken) {
		if (userToken == null)
			return null;

		return UserTokenDto.builder()
			.username(userToken.getUsername())
			.email(userToken.getEmail())
			// .authorityDtoSet(userToken.getAuthorities().stream()
			// 	.map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
			// 	.collect(Collectors.toSet()))
			.build();
	}
}