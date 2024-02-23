package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberJoinResponseDto {

	private MemberInfoDto memberInfoDto;
	private TokenDto jwt;

	public MemberJoinResponseDto(Member member, TokenDto jwt) {
		memberInfoDto = new MemberInfoDto(
			member.getNickname()
			, member.getProfileImage().toLowerCase()
			, member.getCreatedAt().toString()
			, member.getUpdatedAt().toString());
		this.jwt = jwt;
	}
}
