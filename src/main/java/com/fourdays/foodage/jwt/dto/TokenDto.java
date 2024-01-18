package com.fourdays.foodage.jwt.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TokenDto {

	private String token; // todo : RT 추가 구현 후, AT-RT로 구분 필요

	public String getJwt() {
		return token;
	}
}