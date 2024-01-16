package com.fourdays.foodage.user.domain.vo;

import lombok.Getter;

@Getter
public class UserId {

	private Long userId;

	public UserId(Long userId) {
		this.userId = userId;
	}
}
