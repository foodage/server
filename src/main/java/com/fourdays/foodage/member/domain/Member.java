package com.fourdays.foodage.member.domain;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.member.exception.MemberInvalidStateException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.oauth.domain.OauthId;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member")
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

	@ManyToMany
	@JoinTable(
		name = "member_token_authority",
		joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	private Set<Authority> authorities;

	public Member(Long id, OauthId oauthId, String accountEmail, String nickname, String profileUrl, String state,
		LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime lastLoginAt, Set<Authority> authorities) {
		this.id = id;
		this.oauthId = oauthId;
		this.accountEmail = accountEmail;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.state = MemberState.NORMAL.name();
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.lastLoginAt = lastLoginAt;
		this.authorities = authorities;
	}

	public void validateState() {
		if (state == MemberState.BLOCK.name()) {
			throw new MemberInvalidStateException(ResultCode.ERR_MEMBER_INVALID, LoginResult.BLOCKED);
		}
		if (state == MemberState.LEAVE.name()) {
			throw new MemberInvalidStateException(ResultCode.ERR_MEMBER_INVALID, LoginResult.LEAVED);
		}
	}

	public void updateLastLoginAt() {
		lastLoginAt = LocalDateTime.now();
	}

	public void leaved() {
		if (state == MemberState.LEAVE.name()) {
			throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_ALREADY_LEAVED);
		}
		oauthId = null;
		nickname = "탈퇴한 사용자";
		profileUrl = null;
		state = MemberState.LEAVE.name();
	}
}
