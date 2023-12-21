package com.fourdays.foodage.user.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.common.enums.UserState;
import com.fourdays.foodage.common.exception.UserException;
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

@Table(name = "tb_user")
@Entity
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

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
	private int state;

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public User(Long id, OauthId oauthId, String accountEmail, String nickname, String profileUrl, int state,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.oauthId = oauthId;
		this.accountEmail = accountEmail;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
		this.state = UserState.NORMAL.getCode();
		;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void leaved(User user) {
		if (this.state == UserState.LEAVE.getCode()) {
			throw new UserException(ResultCode.ERR_USER_ALREADY_LEAVED);
		}
		this.oauthId = null;
		this.nickname = "탈퇴한 사용자";
		this.profileUrl = null;
		this.state = UserState.LEAVE.getCode();
	}
}
