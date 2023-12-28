package com.fourdays.foodage.jwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.jwt.domain.UserToken;
import com.fourdays.foodage.jwt.domain.UserTokenRepository;

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	private final UserTokenRepository userTokenRepository;

	public CustomUserDetailsService(UserTokenRepository userTokenRepository) {
		this.userTokenRepository = userTokenRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		return userTokenRepository.findOneWithAuthoritiesByUsername(username)
			.map(userToken -> createUser(username, userToken))
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	private org.springframework.security.core.userdetails.User createUser(String username, UserToken userToken) {
		if (!userToken.isActivated()) {
			throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
		}

		List<GrantedAuthority> grantedAuthorities = userToken.getAuthorities().stream()
			.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
			.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(userToken.getUsername(),
			userToken.getOauthToken(),
			grantedAuthorities);
	}
}
