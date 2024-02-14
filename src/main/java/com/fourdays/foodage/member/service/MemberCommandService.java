package com.fourdays.foodage.member.service;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.Role;
import com.fourdays.foodage.jwt.handler.TokenProvider;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;
import com.fourdays.foodage.member.dto.MemberJoinResponseDto;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.oauth.domain.OauthId;

import lombok.extern.slf4j.Slf4j;

/**
 * author         : ebkim, jhjung
 * date           : 2023/10/19
 * description    : db를 업데이트 하거나 상태를 변경하는 작업을 여기에 작성합니다.
 */
@Service
@Slf4j
public class MemberCommandService {

	private final MemberQueryService memberQueryService;
	private final MemberRepository memberRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final TokenProvider tokenProvider;
	private final PasswordEncoder passwordEncoder;

	public MemberCommandService(MemberQueryService memberQueryService, MemberRepository memberRepository,
		AuthenticationManagerBuilder authenticationManagerBuilder, TokenProvider tokenProvider,
		PasswordEncoder passwordEncoder) {
		this.memberQueryService = memberQueryService;
		this.memberRepository = memberRepository;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
		this.tokenProvider = tokenProvider;
		this.passwordEncoder = passwordEncoder;
	}

	// @Transactional
	public MemberJoinResponseDto join(OauthId oauthId, String accountEmail, String nickname, String profileImage) {

		Optional<Member> findMember = memberRepository.findByAccountEmail(accountEmail);
		if (findMember.isPresent())
			throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_ALREADY_JOINED);

		// create member
		Authority authority = Authority.builder()
			.authorityName(Role.MEMBER.getRole())
			.build();
		String credential = UUID.randomUUID().toString();
		log.debug("# credential (plain) : {}", credential);
		Member member = Member.builder()
			.oauthId(oauthId)
			.accountEmail(accountEmail)
			.credential(passwordEncoder.encode(credential))
			.nickname(nickname)
			.profileImage(profileImage)
			.authorities(Collections.singleton(authority))
			.build();

		Long id = memberRepository.save(member).getId();
		log.debug(
			"\n#--------- saved member ---------#\nid : {}\ncredential : {}\naccountEmail : {}\n#--------------------------------#",
			id,
			credential,
			accountEmail
		);

		// jwt 발행 (at & rt)
		UsernamePasswordAuthenticationToken authenticationToken =
			new UsernamePasswordAuthenticationToken(nickname, credential);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		TokenDto jwt = tokenProvider.createToken(authentication);
		log.debug("\n#--- accessToken : {}\n#--- refreshToken : {}", jwt.accessToken(), jwt.refreshToken());

		MemberJoinResponseDto memberJoinResponseDto = new MemberJoinResponseDto(member, jwt);
		return memberJoinResponseDto;
	}

	@Transactional
	public void login(OauthId oauthId, String accountEmail) {
		log.debug("# oauthServerId : {}\noauthServerType : {}\naccountEmail : {}", oauthId.getOauthServerId(),
			oauthId.getOauthServerType(), accountEmail);

		Optional<Member> findMember = memberRepository.findByOauthIdAndAccountEmail(oauthId, accountEmail);
		if (!findMember.isPresent()) {
			throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND);
		}

		// 블락, 휴면, 탈퇴 상태인지 확인
		findMember.get().validateState();

		// 약관 동의 여부 확인

		// 로그인 히스토리 추가

		// 마지막 로그인 일시 업데이트
		findMember.get().updateLastLoginAt();
	}

	@Transactional
	public void leave(long memberId) {
		Optional<Member> findMember = memberRepository.findById(memberId);
		if (findMember.isEmpty()) {
			throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND);
		}

		findMember.get().leaved();

		log.debug("\n#--- leaved member ---#\nid : {}\nnickname : {}\n#--------------------#",
			findMember.get().getId(),
			findMember.get().getNickname()
		);
	}
}
