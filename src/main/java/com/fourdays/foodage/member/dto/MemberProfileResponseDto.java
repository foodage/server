package com.fourdays.foodage.member.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.Getter;

@Getter
public class MemberProfileResponseDto {

	private CharacterType character;

	private String nickname;

	private OauthServerType socialType; // todo: oauthServerType -> socialType 통일 고려

	private String accountEmail;

	private long daysSinceFoodageSignup;

	private int foodageCount;

	public MemberProfileResponseDto(final Member member, final int foodageCount) {

		this.character = member.getCharacter();
		this.nickname = member.getNickname();
		this.socialType = member.getOauthId().getOauthServerType();
		this.accountEmail = member.getAccountEmail();

		LocalDateTime now = LocalDateTime.now();
		this.daysSinceFoodageSignup = ChronoUnit.DAYS.between(
			member.getCreatedAt(), now);
		this.foodageCount = foodageCount;
	}
}
