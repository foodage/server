package com.fourdays.foodage.member.dto;

import java.time.LocalDateTime;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInfoDto {

	private String nickname;

	private String profileImage;

	private CharacterType characater;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public MemberInfoDto(Member member) {
		this.nickname = member.getNickname();
		this.profileImage = member.getProfileImage();
		this.characater = member.getCharacter();
		this.createdAt = member.getCreatedAt();
		this.updatedAt = member.getUpdatedAt();
	}
}
