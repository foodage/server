package com.fourdays.foodage.member.domain;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;

import com.fourdays.foodage.common.domain.BaseTimeEntity;
import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.member.exception.MemberJoinedException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.oauth.domain.OauthId;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
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
@DynamicUpdate // 오버헤드 발생할 수 있으므로 변경이 자주 일어나지 않는 Entity에는 주의해서 사용
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Embedded
	@Column(name = "oauth_id") // 탈퇴 시 null
	private OauthId oauthId;

	@Column(name = "account_email", nullable = false)
	private String accountEmail;

	@Column(name = "credential", nullable = false)
	private String credential; // 사용자가 입력하지는 않음, 서버에서 생성-관리하는 비밀번호

	@Column(name = "nickname", length = 64, unique = true)
	private String nickname;

	@Column(name = "character_type")
	@Enumerated(EnumType.STRING)
	private CharacterType character;

	@Column(name = "state", nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	private MemberState state;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;

	@ManyToMany
	@JoinTable(
		name = "member_authority",
		joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	private Set<Authority> authorities;

	public LoginResult getLoginResultByMemberState() {

		switch (state) {
			case TEMP_JOIN -> { // 가입 진행중
				return LoginResult.JOIN_IN_PROGRESS;
			}
			case NORMAL -> { // 정상 유저
				return LoginResult.JOINED;
			}
			case BLOCK -> { // 제한된 사용자
				return LoginResult.BLOCKED;
			}
			case LEAVE -> { // 탈퇴한 사용자
				return LoginResult.LEAVED;
			}
			default -> {
				return LoginResult.INVALID;
			}
		}
	}

	public void validateMemberIsTempJoin() {

		if (state != MemberState.TEMP_JOIN &&
			nickname != null &&
			character != null) {
			throw new MemberJoinedException(ResultCode.ERR_MEMBER_ALREADY_JOINED);
		}
	}

	public void updateCredential(String encCredential) {
		credential = encCredential;
	}

	public void updateLastLoginAt() {
		lastLoginAt = LocalDateTime.now();
	}

	public void completedJoin(String nickname, CharacterType character,
		String credential) {
		this.credential = credential;
		this.nickname = nickname;
		this.character = character;
		this.state = MemberState.NORMAL;
		updateLastLoginAt();
	}

	public void leaved() {
		if (state == MemberState.LEAVE) {
			throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_ALREADY_LEAVED);
		}
		oauthId = null;
		nickname = "탈퇴한 사용자";
		state = MemberState.LEAVE;
	}
}
