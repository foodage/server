package com.fourdays.foodage.member.domain;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.service.RedisService;
import com.fourdays.foodage.member.vo.MemberId;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LeaveRequestRepository extends RedisService {

	public LeaveRequestRepository(RedisTemplate<String, Object> redisTemplate) {
		super(redisTemplate);
	}

	public void save(MemberId memberId) {

		String key = generateKey(memberId);
		LocalDateTime requestedAt = LocalDateTime.now();
		long EXPIRATION_DAYS = 30L;

		this.save(key, requestedAt, 10, TimeUnit.SECONDS);
	}

	public void delete(MemberId memberId) {

		String key = generateKey(memberId);
		this.delete(key);
	}

	public String generateKey(MemberId memberId) {

		String key =
			"social:" + memberId.oauthServerType() +
				":email:" + memberId.accountEmail() +
				":leave:request";

		log.debug("generateKey() => {}", key);
		return key;
	}
}
