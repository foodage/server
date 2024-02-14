package com.fourdays.foodage.jwt.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fourdays.foodage.jwt.handler.JwtFilter;
import com.fourdays.foodage.jwt.handler.TokenProvider;

public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private TokenProvider tokenProvider;

	public JwtConfig(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void configure(HttpSecurity http) {

		http.addFilterBefore(
			new JwtFilter(tokenProvider)
			, UsernamePasswordAuthenticationFilter.class
		);
	}
}
