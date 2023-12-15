package com.fourdays.foodage.oauth.util;

import com.fourdays.foodage.oauth.domain.OauthMember;

/**
 * author         : ebkim <br/>
 * date           : 2023/12/14 <br/>
 * description    : 각 인증 서버로부터 Token을 발급받습니다. 해당 Token으로 사용자 정보 등을 조회할 수 있습니다. <br/>
 */
public interface OauthTokenProvider {

	// 해당 클래스(구현체)가 지원 가능한 OauthServerType을 반환합니다.
	OauthServerType getSupportedServerType();

	// Auth code로 인증 서버로부터 access token을 발급받아 가져옵니다.
	OauthMember getOAuthToken(String code);
}