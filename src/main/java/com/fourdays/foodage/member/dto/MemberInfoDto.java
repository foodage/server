package com.fourdays.foodage.member.dto;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.member.domain.Member;

public record MemberInfoDto(

	String nickname,
	String profileImage,
	CharacterType character,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

	public MemberInfoDto(Member member) {
		this(member.getNickname(), member.getProfileImage(), member.getCharacter(),
			member.getCreatedAt(), member.getUpdatedAt());
	}
}