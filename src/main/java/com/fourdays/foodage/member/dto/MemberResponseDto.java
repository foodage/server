package com.fourdays.foodage.member.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.oauth.domain.OauthId;

import lombok.Getter;

@Getter
public class MemberResponseDto {

	private Long id;

	private OauthId oauthId;

	private String accountEmail;

	private String nickname;

	private String profileImage;

	private CharacterType character;

	private MemberState state;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private LocalDateTime lastLoginAt;

	private Set<Authority> authorities;

	public MemberResponseDto(Member member) {
		this.id = member.getId();
		this.oauthId = member.getOauthId();
		this.accountEmail = member.getAccountEmail();
		this.nickname = member.getNickname();
		this.profileImage = member.getProfileImage();
		this.character = member.getCharacter();
		this.state = member.getState();
		this.createdAt = member.getCreatedAt();
		this.updatedAt = member.getUpdatedAt();
		this.lastLoginAt = member.getLastLoginAt();
		this.authorities = member.getAuthorities();
	}
}
