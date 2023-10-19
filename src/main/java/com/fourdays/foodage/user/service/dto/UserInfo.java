package com.fourdays.foodage.user.service.dto;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.user.domain.User;

import jakarta.persistence.Column;

public class UserInfo extends BaseTimeEntity {

	@Column(name = "nickname", nullable = false, length = 64)
	private String nickname;

	@Column(name = "profile_url")
	private String profileUrl;

	public UserInfo(User user) {
		this.nickname = user.getNickname();
		this.profileUrl = user.getProfileUrl();
	}
}
