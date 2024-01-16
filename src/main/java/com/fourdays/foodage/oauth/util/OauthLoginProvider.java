package com.fourdays.foodage.oauth.util;

import com.fourdays.foodage.oauth.domain.OauthMember;

/**
 * author         : ebkim <br/>
 * date           : 2023/12/14 <br/>
 * description    : 각 간편 로그인 서비스 인증 서버로부터 Token을 발급받고,
 *                  해당 Token으로 사용자 정보를 조회합니다. <br/>
 */
public interface OauthLoginProvider {

	// 해당 클래스(구현체)가 지원 가능한 OauthServerType을 반환합니다.
	OauthServerType getSupportedServerType();

	// Auth Code로 인증 서버로부터 인가 Token을 발급받아 가져온 후,
	// 해당 인가 Token으로 간편 로그인 사용자 정보를 가져와 반환합니다.
	OauthMember getTokenAndMemberInfo(String code);
}