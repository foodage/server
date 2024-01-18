package com.fourdays.foodage.jwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.jwt.domain.UserToken;
import com.fourdays.foodage.jwt.domain.UserTokenRepository;
import com.fourdays.foodage.jwt.dto.UserTokenDto;
import com.fourdays.foodage.jwt.exception.DuplicateMemberException;
import com.fourdays.foodage.jwt.exception.NotFoundMemberException;
import com.fourdays.foodage.jwt.util.SecurityUtil;

import jakarta.validation.Valid;

@Service
public class UserService {
	private final UserTokenRepository userTokenRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserTokenRepository userTokenRepository, PasswordEncoder passwordEncoder) {
		this.userTokenRepository = userTokenRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public UserTokenDto signup(@Valid UserTokenDto userTokenDto) {
		if (userTokenRepository.findOneWithAuthoritiesByUsername(userTokenDto.getUsername()).orElse(null) != null) {
			throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
		}

		Authority authority = Authority.builder()
			.authorityName("ROLE_USER")
			.build();

		UserToken userToken = UserToken.builder()
			.username(userTokenDto.getUsername())
			.email(passwordEncoder.encode(userTokenDto.getEmail()))
			// .authorities(Collections.singleton(authority))
			.activated(true)
			.build();

		return UserTokenDto.from(userTokenRepository.save(userToken));
	}

	@Transactional(readOnly = true)
	public UserTokenDto getUserWithAuthorities(String username) {
		return UserTokenDto.from(userTokenRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
	}

	@Transactional(readOnly = true)
	public UserTokenDto getMyUserWithAuthorities() {
		return UserTokenDto.from(
			SecurityUtil.getCurrentUsername()
				.flatMap(userTokenRepository::findOneWithAuthoritiesByUsername)
				.orElseThrow(() -> new NotFoundMemberException("User not found"))
		);
	}
}
