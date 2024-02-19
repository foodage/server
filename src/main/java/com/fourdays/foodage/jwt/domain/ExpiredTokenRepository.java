package com.fourdays.foodage.jwt.domain;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ExpiredTokenRepository {

	private final RedisTemplate<Long, Object> redisTemplate;

	public ExpiredTokenRepository(RedisTemplate<Long, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(ExpiredToken expiredToken) {
		redisTemplate.opsForValue().set(expiredToken.getKey(), expiredToken.getValue());
	}

	public Object findByKey(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
