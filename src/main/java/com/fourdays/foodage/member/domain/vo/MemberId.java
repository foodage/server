package com.fourdays.foodage.member.domain.vo;

import lombok.Getter;

@Getter
public class MemberId {

	private Long memberId;

	public MemberId(Long memberId) {
		this.memberId = memberId;
	}
}
