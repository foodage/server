package com.fourdays.foodage.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum SearchType {

	DATE,
	KEYWORD,
	EXTRA;

	@JsonCreator
	public static SearchType of(String value) {
		for (SearchType st : values()) {
			if (st.name().equals(value.toUpperCase())) {
				return st;
			}
		}
		throw new IllegalArgumentException("Invalid SearchType value: " + value);
	}
}
