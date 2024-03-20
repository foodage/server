package com.fourdays.foodage.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SearchBy {

	ALL(""),
	NAME("name"),
	IDENTIFIER("identifier"),
	LOGIN_ID("loginId"),
	EMAIL("email"),
	TYPE("type"),
	FILE_NAME("fileName"),
	VC_SCHEMA_NAME("vcSchemaName"),
	VC_CATEGORY_NAME("vcCategoryName"),
	USER_EMAIL("userEmail"),
	USER_DA("userDa"),
	CREATED_BY("createdBy"),
	SERVICE_CODE("serviceCode"),
	DESCRIPTION("description");

	private final String columnName;

	@JsonCreator
	public static SearchBy of(String value) {
		for (SearchBy s : values()) {
			if (s.getColumnName().equals(value)) {
				return s;
			}
		}
		throw new IllegalArgumentException("Invalid SearchBy value: " + value);
	}

	public String getColumnName() {
		return columnName;
	}
}
