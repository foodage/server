package com.fourdays.foodage.jwt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtType {

	ACCESS_TOKEN("Access-Token"),
	REFRESH_TOKEN("Refresh-Token");

	String headerName;
}
