package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.common.enums.LoginResult;

public record MemberLoginResultDto(

	String nickname,
	LoginResult loginResult
) {
}
