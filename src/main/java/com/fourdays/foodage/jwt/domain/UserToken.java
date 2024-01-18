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
public class UserToken {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", length = 50, unique = true)
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "activated")
	private boolean activated;

	@Builder
	public UserToken(Long id, String username, String email, boolean activated) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.activated = activated;
	}
}
