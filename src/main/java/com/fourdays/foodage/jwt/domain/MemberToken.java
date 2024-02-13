package com.fourdays.foodage.jwt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_token")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberToken {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nickname", length = 50, unique = true)
	private String nickname;

	@Column(name = "email")
	private String email;

	@Column(name = "activated")
	private boolean activated;

	@Builder
	public MemberToken(Long id, String nickname, String email, boolean activated) {
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.activated = activated;
	}
}
