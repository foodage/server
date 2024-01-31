package com.fourdays.foodage.jwt.dto;

import java.util.Set;

import com.fourdays.foodage.jwt.domain.MemberToken;

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
public class MemberTokenDto {

	@NotNull
	@Size(min = 3, max = 50)
	private String nickname;

	@NotNull
	@Size(min = 3, max = 100)
	private String email;

	// private Set<AuthorityDto> authorityDtoSet;

	@Builder
	public MemberTokenDto(String nickname, String email, Set<AuthorityDto> authorityDtoSet) {
		this.nickname = nickname;
		this.email = email;
		// this.authorityDtoSet = authorityDtoSet;
	}

	public static MemberTokenDto from(MemberToken memberToken) {
		if (memberToken == null)
			return null;

		return MemberTokenDto.builder()
			.nickname(memberToken.getNickname())
			.email(memberToken.getEmail())
			// .authorityDtoSet(memberToken.getAuthorities().stream()
			// 	.map(authority -> AuthorityDto.builder().authorityName(authority.getAuthorityName()).build())
			// 	.collect(Collectors.toSet()))
			.build();
	}
}