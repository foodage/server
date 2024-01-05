package com.fourdays.foodage.member.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.member.exception.MemberException;
import com.fourdays.foodage.member.exception.MemberStateException;
import com.fourdays.foodage.oauth.domain.OauthId;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tb_member")
@Entity
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Embedded
	@Column(name = "oauth_id")
	private OauthId oauthId;

	@Column(name = "account_email")
	private String accountEmail;

	@Column(name = "nickname", nullable = false, length = 64)
	private String nickname;

	@Column(name = "profile_url")
	private String profileUrl;

	@Column(name = "state")
	private String state;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;

	public Member(Long id, OauthId oauthId, String accountEmail, String nickname, String profileUrl, String state,
		LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLoginAt) {
		this.id = id;
		this.oauthId = oauthId;
		this.accountEmail = accountEmail;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.state = MemberState.NORMAL.name();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.lastLoginAt = lastLoginAt;
	}

	public void validateState() {
		if (state == MemberState.BLOCK.name()) {
			throw new MemberStateException(ResultCode.ERR_MEMBER_INVALID, LoginResult.BLOCKED);
		}
		if (state == MemberState.LEAVE.name()) {
			throw new MemberStateException(ResultCode.ERR_MEMBER_INVALID, LoginResult.LEAVED);
		}
	}

	public void updateLastLoginAt() {
		lastLoginAt = LocalDateTime.now();
	}

	public void leaved() {
		if (state == MemberState.LEAVE.name()) {
			throw new MemberException(ResultCode.ERR_MEMBER_ALREADY_LEAVED);
		}
		oauthId = null;
		nickname = "탈퇴한 사용자";
		profileUrl = null;
		state = MemberState.LEAVE.name();
	}
}
