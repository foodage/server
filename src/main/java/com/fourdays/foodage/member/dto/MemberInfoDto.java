package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberInfoDto {

	private String nickname;

	private String profileImage;

	private String createdAt;

	private String updatedAt;

	public MemberInfoDto(Member member) {
		this.nickname = member.getNickname();
		this.profileImage = member.getProfileImage();
		this.createdAt = member.getCreatedAt().toString();
		this.updatedAt = member.getUpdatedAt().toString();
	}
}
