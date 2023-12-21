package com.fourdays.foodage.oauth.util;

/**
 * author         : ebkim <br/>
 * date           : 2023/12/14 <br/>
 * description    : 인증을 위한 Request Uri를 Build합니다. <br/>
 */
public interface OauthRequestUriProvider {

	// 해당 클래스(구현체)가 지원 가능한 OauthServerType을 반환합니다.
	OauthServerType getSupportedServerType();

	// 로그인, 회원가입 화면을 그려주는 Uri을 생성하여 반환합니다.
	String buildRequestUri();
}