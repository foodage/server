package com.fourdays.foodage.oauth.util.kakao;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.MediaType.*;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.fourdays.foodage.oauth.dto.KakaoMember;
import com.fourdays.foodage.oauth.dto.KakaoToken;

public interface KakaoApiClient {

	@PostExchange(url = "https://kauth.kakao.com/oauth/token", contentType = APPLICATION_FORM_URLENCODED_VALUE)
	KakaoToken fetchToken(@RequestParam MultiValueMap<String, String> params);

	@GetExchange("https://kapi.kakao.com/v2/user/me")
	KakaoMember fetchMemberInfo(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}