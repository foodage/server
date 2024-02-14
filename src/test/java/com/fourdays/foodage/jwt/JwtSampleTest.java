package com.fourdays.foodage.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.handler.TokenProvider;

@SpringBootTest
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=none")
public class JwtSampleTest {

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("jwt 발행 예제 (at & rt)")
	public void authorize() {

		String nickname = "하이요";
		String plainCredential = "ad3fd770-4f5c-4df7-bf69-56ae0dd73dee";
		// 실제 코드에서는 암호화된 값으로 UsernamePasswordAuthenticationToken 생성해야함
		// String encCredential = passwordEncoder.encode(plainCredential);

		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(nickname, plainCredential);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenDto jwt = tokenProvider.createToken(authentication);
		System.out.println("access token : " + jwt.accessToken());
		System.out.println("refresh token : " + jwt.refreshToken());

		// ResponseEntity<>에 함께 전달
		// HttpHeaders httpHeaders = new HttpHeaders();
		// httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

		System.out.println(jwt);
	}
}
