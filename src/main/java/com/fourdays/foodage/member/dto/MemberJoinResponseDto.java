package com.fourdays.foodage.member.dto;

import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.member.domain.Member;

public record MemberJoinResponseDto(

	MemberInfoDto memberInfoDto,
	TokenDto jwt
) {

	public MemberJoinResponseDto(Member member, TokenDto jwt) {
		this(new MemberInfoDto(
			member.getNickname(), member.getCharacter(),
			member.getCreatedAt(), member.getUpdatedAt()
		), jwt);
	}
}
