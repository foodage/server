package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.common.enums.LoginResult;

public record MemberLoginInfoDto(

	long memberId,
	String nickname,
	LoginResult loginResult
) {
}
