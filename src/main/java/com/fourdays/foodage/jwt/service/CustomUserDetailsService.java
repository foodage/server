package com.fourdays.foodage.jwt.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Component("userDetailsService")
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public CustomUserDetailsService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		log.debug("# authenticate() --...--> loadUserByUsername() execute");
		log.debug("# username : {}", username);

		return memberRepository.findOneWithAuthoritiesByNickname(username)
			.map(member -> createUserDetails(member))
			.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	private User createUserDetails(Member member) {
		// todo : 사용자 활성 여부 확인하는 코드 추가

		List<GrantedAuthority> grantedAuthorities = member.getAuthorities().stream()
			.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
			.collect(Collectors.toList());

		return new User(member.getNickname(), member.getCredential(),
			grantedAuthorities);
	}
}
