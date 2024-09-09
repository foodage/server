package com.fourdays.foodage.jwt.domain;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.service.RedisService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ExpiredTokenRepository extends RedisService {

	public ExpiredTokenRepository(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
	}

	public void save(ExpiredToken expiredToken) {

		String key = expiredToken.getRefreshToken();
		Map<String, Object> value = expiredToken.getRefreshTokenValues();
		long EXPIRATION_DAYS = 60L; // 60일 뒤 redis에서 삭제

		this.save(key, value, EXPIRATION_DAYS, TimeUnit.DAYS);

		log.debug(
			"\n#--------- saved expired refreshToken ---------#\ntoken : {}\ncreatedAt : {}\nexpiredAt : {} days later\n#----------------------------------------------#",
			expiredToken.getRefreshToken(),
			expiredToken.getCreatedAt(),
			Duration.ofDays(EXPIRATION_DAYS).toDays()
		);
	}
}
