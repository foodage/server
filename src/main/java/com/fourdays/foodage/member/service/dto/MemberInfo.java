package com.fourdays.foodage.member.service.dto;

import com.fourdays.foodage.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberInfo {

	private String nickname;

	private String profileUrl;

	private String createdAt;

	private String updatedAt;

	public MemberInfo(Member member) {
		this.nickname = member.getNickname();
		this.profileUrl = member.getProfileUrl();
		this.createdAt = member.getCreatedAt().toString();
		this.updatedAt = member.getUpdatedAt().toString();
	}
}
