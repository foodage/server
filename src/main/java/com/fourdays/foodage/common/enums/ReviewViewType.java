package com.fourdays.foodage.common.enums;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.home.exception.NotSupportedViewTypeException;

import lombok.Getter;

@Getter
public enum ReviewViewType {

	WEEKLY,
	MONTHLY;

	@JsonCreator
	public static ReviewViewType of(String jsonValue) {
		for (ReviewViewType viewType : values()) {
			if (viewType.name().equalsIgnoreCase(jsonValue)) {
				return viewType;
			}
		}
		throw new NotSupportedViewTypeException(ExceptionInfo.ERR_NOT_SUPPORTED_CHARACTER_TYPE);
	}

	@JsonValue
	public String toLowerCase() {
		return this.toString().toLowerCase(Locale.ENGLISH);
	}
}
