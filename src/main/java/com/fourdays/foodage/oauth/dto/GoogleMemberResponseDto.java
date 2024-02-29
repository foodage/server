package com.fourdays.foodage.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.util.OauthServerType;

@JsonNaming(value = SnakeCaseStrategy.class)
public record GoogleMemberResponseDto(
	String id,
	String email
) {

	public OauthMember toOauthMember() {
		return OauthMember.builder()
			.oauthId(new OauthId(String.valueOf(id), OauthServerType.GOOGLE))
			.accountEmail(email)
			.build();
	}
}