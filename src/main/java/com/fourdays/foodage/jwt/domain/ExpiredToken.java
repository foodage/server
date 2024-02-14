package com.fourdays.foodage.jwt.domain;

import java.time.LocalDateTime;

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
@Table(name = "expired_token")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpiredToken {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "expired_at")
	private LocalDateTime expiredAt;

	public ExpiredToken(Long id, Long memberId, String refreshToken, LocalDateTime expiredAt) {
		this.id = id;
		this.memberId = memberId;
		this.refreshToken = refreshToken;
		this.expiredAt = expiredAt;
	}
}
