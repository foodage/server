package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.member.domain.Member;

import lombok.Getter;

@Getter
public class MemberJoinResponseDto {

	private MemberInfoDto memberInfoDto;
	private TokenDto tokenDto;

	public MemberJoinResponseDto(Member member, TokenDto tokenDto) {
		memberInfoDto = new MemberInfoDto(member.getNickname(), member.getProfileUrl(),
			member.getCreatedAt().toString(), member.getUpdatedAt().toString());
		this.tokenDto = tokenDto;
	}
}
