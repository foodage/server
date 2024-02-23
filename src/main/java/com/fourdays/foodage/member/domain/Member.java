package com.fourdays.foodage.member.domain;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fourdays.foodage.common.enums.CharacterType;
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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "member")
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Embedded
	@Column(name = "oauth_id", nullable = false)
	private OauthId oauthId;

	@Column(name = "account_email", nullable = false)
	private String accountEmail;

	@Column(name = "credential", nullable = false)
	private String credential; // 사용자가 입력하지는 않음, 서버에서 생성-관리하는 비밀번호

	@Column(name = "nickname", length = 64)
	private String nickname;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "character_type")
	@Enumerated(EnumType.STRING)
	private CharacterType character;

	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private MemberState state;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	@NotNull
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

	public void validateState() {
		if (state == MemberState.BLOCK) {
			throw new MemberInvalidStateException(ResultCode.ERR_MEMBER_INVALID, LoginResult.BLOCKED);
		}
		if (state == MemberState.LEAVE) {
			throw new MemberInvalidStateException(ResultCode.ERR_MEMBER_INVALID, LoginResult.LEAVED);
		}
	}

	public void updateCredential(String encCredential) {
		credential = encCredential;
	}

	public void updateLastLoginAt() {
		lastLoginAt = LocalDateTime.now();
	}

	public void completedJoin(String nickname, String profileImage, CharacterType character,
		String credential) {
		this.credential = credential;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.character = character;
		this.state = MemberState.NORMAL;
	}

	public void leaved() {
		if (state == MemberState.LEAVE) {
			throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_ALREADY_LEAVED);
		}
		oauthId = null;
		nickname = "탈퇴한 사용자";
		profileImage = null;
		state = MemberState.LEAVE;
	}
}
