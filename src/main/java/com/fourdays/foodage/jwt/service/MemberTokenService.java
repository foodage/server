package com.fourdays.foodage.jwt.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.jwt.domain.MemberToken;
import com.fourdays.foodage.jwt.domain.MemberTokenRepository;
import com.fourdays.foodage.jwt.dto.MemberTokenDto;
import com.fourdays.foodage.jwt.enums.Role;
import com.fourdays.foodage.jwt.exception.DuplicateMemberException;
import com.fourdays.foodage.jwt.exception.NotFoundMemberException;
import com.fourdays.foodage.jwt.util.SecurityUtil;

import jakarta.validation.Valid;

@Service
public class MemberTokenService {

	private final MemberTokenRepository memberTokenRepository;
	private final PasswordEncoder passwordEncoder;

	public MemberTokenService(MemberTokenRepository memberTokenRepository, PasswordEncoder passwordEncoder) {
		this.memberTokenRepository = memberTokenRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public MemberTokenDto signup(@Valid MemberTokenDto memberTokenDto) {
		if (memberTokenRepository.findOneWithAuthoritiesByNickname(memberTokenDto.getNickname())
			.orElse(null) != null) {
			throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
		}

		Authority authority = Authority.builder()
			.authorityName(Role.MEMBER.getRole())
			.build();

		MemberToken memberToken = MemberToken.builder()
			.nickname(memberTokenDto.getNickname())
			.email(passwordEncoder.encode(memberTokenDto.getEmail()))
			// .authorities(Collections.singleton(authority))
			.activated(true)
			.build();

		return MemberTokenDto.from(memberTokenRepository.save(memberToken));
	}

	@Transactional(readOnly = true)
	public MemberTokenDto getMemberWithAuthorities(String username) {
		return MemberTokenDto.from(memberTokenRepository.findOneWithAuthoritiesByNickname(username).orElse(null));
	}

	@Transactional(readOnly = true)
	public MemberTokenDto getMemberWithAuthorities() {
		return MemberTokenDto.from(
			SecurityUtil.getCurrentUsername()
				.flatMap(memberTokenRepository::findOneWithAuthoritiesByNickname)
				.orElseThrow(() -> new NotFoundMemberException("Member not found"))
		);
	}
}
