package com.fourdays.foodage.jwt.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisHash;

import com.fourdays.foodage.jwt.enums.JwtType;

import jakarta.persistence.Id;
import lombok.Getter;

@RedisHash("expired_token")
@Getter
public class ExpiredToken {

	@Id
	private final String refreshToken;

	private LocalDateTime createdAt;

	public ExpiredToken(String refreshToken) {
		this.refreshToken = refreshToken;
		this.createdAt = LocalDateTime.now();
	}

	public Map<String, Object> getRefreshTokenValues() {
		Map<String, Object> values = new HashMap<>();
		values.put("type", JwtType.REFRESH_TOKEN.name().toLowerCase());
		values.put("created_at", createdAt.toString());
		return values;
	}
}
