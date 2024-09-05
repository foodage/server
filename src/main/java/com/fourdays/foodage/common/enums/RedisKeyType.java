package com.fourdays.foodage.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisKeyType {

	LEAVE_REQUEST("leave:request"),
	;

	private final String eventId;
}
