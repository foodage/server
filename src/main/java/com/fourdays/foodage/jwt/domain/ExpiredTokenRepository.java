package com.fourdays.foodage.jwt.domain;

import java.time.Duration;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExpiredTokenRepository {

	private final RedisTemplate<String, Object> redisTemplate;

	public ExpiredTokenRepository(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(ExpiredToken expiredToken) {
		long expirationDays = 60L; // 60일 뒤 redis db에서 삭제

		redisTemplate.opsForValue().set(expiredToken.getRefreshToken(),
			expiredToken.getRefreshTokenValues(),
			Duration.ofDays(expirationDays));

		log.debug(
			"\n#--------- saved expired refreshToken ---------#\ntoken : {}\ncreatedAt : {}\nexpiredAt : {} days later\n#--------------------------------#",
			expiredToken.getRefreshToken(),
			expiredToken.getCreatedAt(),
			Duration.ofDays(expirationDays).toDays()
		);
	}

	public Object findByKey(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
