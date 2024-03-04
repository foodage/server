package com.fourdays.foodage.oauth.domain;

import static jakarta.persistence.EnumType.*;

import com.fourdays.foodage.oauth.util.OauthServerType;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OauthId {

	@Column(nullable = false, name = "oauth_server_id")
	private String oauthServerId;

	@Enumerated(STRING)
	@Column(nullable = false, name = "oauth_server")
	private OauthServerType oauthServerType;
}