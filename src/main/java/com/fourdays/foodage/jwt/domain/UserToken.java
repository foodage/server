package com.fourdays.foodage.jwt.domain;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@ManyToMany
	@JoinTable(
		name = "tb_usertoken_authority",
		joinColumns = {@JoinColumn(name = "id", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
	private Set<Authority> authorities;

	@Builder
	public UserToken(Long id, String username, String email, boolean activated, Set<Authority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.activated = activated;
		this.authorities = authorities;
	}
}
