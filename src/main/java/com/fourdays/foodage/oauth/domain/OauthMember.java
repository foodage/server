package com.fourdays.foodage.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class OauthMember {

	private OauthId oauthId;
	private String accountEmail;
}