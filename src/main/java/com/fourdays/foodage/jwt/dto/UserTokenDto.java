package com.fourdays.foodage.jwt.dto;

import lombok.*;
import com.fourdays.foodage.jwt.domain.UserToken;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenDto {

	@NotNull
	@Size(min = 3, max = 50)
	private String username;

	@NotNull
	@Size(min = 3, max = 100)
	private String oauthToken;

	private Set<AuthorityDto> authorityDtoSet;

	public static UserTokenDto from(UserToken userToken) {
		if(userToken == null) return null;

		return UserTokenDto.builder()
			.username(userToken.getUsername())
			.authorityDtoSet(userToken.getAuthorities().stream()
				.map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
				.collect(Collectors.toSet()))
			.build();
	}
}