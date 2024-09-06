package com.fourdays.foodage.common.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.fourdays.foodage.common.enums.RedisKeyType;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.oauth.util.OauthServerType;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

	private final MemberCommandService memberCommandService;

	/**
	 * Creates new {@link MessageListener} for {@code __keyevent@*__:expired} messages.
	 * @param listenerContainer must not be {@literal null}.
	 */
	public RedisKeyExpiredListener(
		@Qualifier("redisMessageListenerContainer") RedisMessageListenerContainer listenerContainer,
		MemberCommandService memberCommandService) {

		super(listenerContainer);
		this.memberCommandService = memberCommandService;
	}

	/**
	 * @param message   redis key
	 * @param pattern   __keyevent@*__:expired
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {

		final String redisKey = new String(message.getBody());
		log.info("");
		log.info("================= [redis key expired] =================");
		log.info("* event pattern : {}", new String(pattern)); // 이벤트를 구독할 때 사용한 패턴
		log.info("* redis channel : {}", new String(message.getChannel())); // 이벤트가 발생한 Redis 채널(db)명
		log.info("* redis key : [{}]", redisKey);

		if (message.toString().contains(RedisKeyType.LEAVE_REQUEST.getEventId())) {
			String[] parts = redisKey.split(":");
			OauthServerType oauthServerType = OauthServerType.fromName(parts[1]);
			String accountEmail = parts[3];

			memberCommandService.completeLeave(new MemberId(oauthServerType, accountEmail));
		}
	}
}
