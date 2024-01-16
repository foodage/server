package com.fourdays.foodage.user.service.dto;

import com.fourdays.foodage.user.domain.User;

import lombok.Getter;

@Getter
public class UserInfo {

	private String nickname;

	private String profileUrl;

	private String createdAt;

	private String updatedAt;

	public UserInfo(User user) {
		this.nickname = user.getNickname();
		this.profileUrl = user.getProfileUrl();
		this.createdAt = user.getCreatedAt().toString();
		this.updatedAt = user.getUpdatedAt().toString();
	}
}
