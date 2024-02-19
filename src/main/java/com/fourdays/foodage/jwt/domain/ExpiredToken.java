package com.fourdays.foodage.jwt.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpiredToken {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Column(name = "member_id")
	// private Long memberId;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "expired_at")
	@CreatedDate
	private LocalDateTime expiredAt;

	public ExpiredToken(String refreshToken) {
		// this.memberId = memberId;
		this.refreshToken = refreshToken;
	}

	public Long getKey() {
		return id;
	}

	public Map<String, Object> getValue() {
		Map<String, Object> values = new HashMap<>();
		values.put("refresh_token", refreshToken);
		values.put("expired_at", expiredAt);
		return values;
	}
}
