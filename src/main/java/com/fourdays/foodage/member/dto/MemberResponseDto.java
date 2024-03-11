package com.fourdays.foodage.member.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.oauth.domain.OauthId;

public record MemberResponseDto(

	Long id,
	OauthId oauthId,
	String accountEmail,
	String nickname,
	String profileImage,
	CharacterType character,
	MemberState state,
	LocalDateTime createdAt,
	LocalDateTime updatedAt,
	LocalDateTime lastLoginAt,
	Set<Authority> authorities
) {

	public MemberResponseDto(Member member) {
		this(
			member.getId(),
			member.getOauthId(),
			member.getAccountEmail(),
			member.getNickname(),
			member.getProfileImage(),
			member.getCharacter(),
			member.getState(),
			member.getCreatedAt(),
			member.getUpdatedAt(),
			member.getLastLoginAt(),
			member.getAuthorities()
		);
	}
}
