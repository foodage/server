package com.fourdays.foodage.jwt.handler;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtFilter extends GenericFilterBean {

	public static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
	private final TokenProvider tokenProvider;

	public JwtFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse
		, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
		String jwt = resolveToken(httpServletRequest);
		String requestURI = httpServletRequest.getRequestURI();

		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}"
				, authentication.getName(), requestURI);
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	private String resolveToken(HttpServletRequest request) {

		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public static String resolveToken(String token) {

		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		return null;
	}
}
