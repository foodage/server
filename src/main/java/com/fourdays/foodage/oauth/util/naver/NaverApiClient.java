package com.fourdays.foodage.oauth.util.naver;

import static org.springframework.http.HttpHeaders.*;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.fourdays.foodage.oauth.dto.NaverMemberResponseDto;
import com.fourdays.foodage.oauth.dto.NaverToken;

public interface NaverApiClient {

	@PostExchange(url = "https://nid.naver.com/oauth2.0/token")
	NaverToken fetchToken(@RequestParam(name = "params") MultiValueMap<String, String> params);

	@GetExchange("https://openapi.naver.com/v1/nid/me")
	NaverMemberResponseDto fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}