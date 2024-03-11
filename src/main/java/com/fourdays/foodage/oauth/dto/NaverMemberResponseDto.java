package com.fourdays.foodage.oauth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.util.OauthServerType;

@JsonNaming(value = SnakeCaseStrategy.class)
public record NaverMemberResponseDto(

	String resultcode,
	String message,
	Response response,
	String accessToken
) {

	public NaverMemberResponseDto withAccessToken(String accessToken) {
		return new NaverMemberResponseDto(resultcode, message, response, accessToken);
	}

	public OauthMember toOauthMember() {
		return OauthMember.builder()
			.oauthId(new OauthId(String.valueOf(response.id), OauthServerType.NAVER))
			.accountEmail(response.email)
			.accessToken(accessToken)
			.build();
	}

	@JsonNaming(value = SnakeCaseStrategy.class)
	public record Response(
		String id,
		String nickname,
		String name,
		String email,
		String gender,
		String age,
		String birthday,
		String profileImage,
		String birthyear,
		String mobile
	) {
	}
}