package com.fourdays.foodage.common.enums;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberState {

	TEMP_JOIN(-1),
	NORMAL(0),
	BLOCK(1),
	LEAVE(2);

	private final int code;

	@JsonValue
	public String toLowerCase() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
}
