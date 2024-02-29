package com.fourdays.foodage.oauth.util.google;

import static org.springframework.http.HttpHeaders.*;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.fourdays.foodage.oauth.dto.GoogleMemberResponseDto;
import com.fourdays.foodage.oauth.dto.GoogleToken;

public interface GoogleApiClient {

	@PostExchange(url = "https://oauth2.googleapis.com/token")
	GoogleToken fetchToken(@RequestParam MultiValueMap<String, String> params);

	@GetExchange("https://www.googleapis.com/userinfo/v2/me")
	GoogleMemberResponseDto fetchMember(@RequestHeader(name = AUTHORIZATION) String bearerToken);
}