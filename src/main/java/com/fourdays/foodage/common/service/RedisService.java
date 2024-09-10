package com.fourdays.foodage.common.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.RedisException;

import lombok.extern.slf4j.Slf4j;

@JsonSerialize
@JsonDeserialize
@Slf4j
public abstract class RedisService<T> {

	protected RedisTemplate<String, Object> redisTemplate;

	public RedisService(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(String key, T value, long ttl, TimeUnit timeUnit) {

		if (value instanceof LocalDateTime) {
			value = (T)value.toString();
		}

		switch (timeUnit) {
			case NANOSECONDS -> {
				redisTemplate.opsForValue().set(key, value, Duration.ofNanos(ttl));
			}
			case MILLISECONDS -> {
				redisTemplate.opsForValue().set(key, value, Duration.ofMillis(ttl));
			}
			case SECONDS -> {
				redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl));
			}
			case MINUTES -> {
				redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(ttl));
			}
			case HOURS -> {
				redisTemplate.opsForValue().set(key, value, Duration.ofHours(ttl));
			}
			case DAYS -> {
				redisTemplate.opsForValue().set(key, value, Duration.ofDays(ttl));
			}
			default -> {
				throw new IllegalArgumentException("Unsupported time unit");
			}
		}

		log.info("---> redis save");
		log.info("* key       : {}", key);
		log.info("* value     : {}", value);
		log.info("* expiredAt : {} days later", Duration.ofDays(ttl).toDays());
		log.info("<--- saved");
	}

	public T find(String key) {
		return (T)redisTemplate.opsForValue().get(key);
	}

	public void delete(String key) {

		T data = find(key);
		if (data == null) {
			throw new RedisException(ExceptionInfo.ERR_BLOCKED_REFRESH_TOKEN);
		}
		redisTemplate.delete(key);
		log.info("-------------------------------------------------------");
		log.info("* {} is deleted", key);
		log.info("* value : {}", data.toString());
		log.info("-------------------------------------------------------");
	}

	public boolean exists(String key) {
		return redisTemplate.hasKey(key);
	}
}
