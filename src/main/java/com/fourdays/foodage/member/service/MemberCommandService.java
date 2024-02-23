package com.fourdays.foodage.member.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.Role;
import com.fourdays.foodage.jwt.service.AuthService;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;
import com.fourdays.foodage.member.dto.MemberJoinResponseDto;
import com.fourdays.foodage.member.exception.MemberAlreadyJoinedException;
import com.fourdays.foodage.member.exception.MemberMismatchAccountEmailException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.member.exception.MemberUnexpectedJoinException;
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
	private final AuthService authService;
	private final PasswordEncoder passwordEncoder;

	public MemberCommandService(MemberQueryService memberQueryService, MemberRepository memberRepository,
		AuthService authService, PasswordEncoder passwordEncoder) {
		this.memberQueryService = memberQueryService;
		this.memberRepository = memberRepository;
		this.authService = authService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Long tempJoin(OauthId oauthId, String accountEmail) {

		// 사용자 정보 임시 저장 후, 추가 정보 입력받아 join() 메소드에서 update로 회원가입 완료 처리
		Optional<Member> findMember = memberRepository.findByAccountEmail(accountEmail);
		if (findMember.isPresent()) {
			throw new MemberAlreadyJoinedException(ResultCode.ERR_MEMBER_ALREADY_JOINED);
		}

		// create temp member info
		Authority authority = Authority.builder()
			.authorityName(Role.MEMBER.getRole())
			.build();
		String credential = authService.createCredential();
		log.debug("# credential (plain) : {}", credential);

		Member member = Member.builder()
			.oauthId(oauthId)
			.accountEmail(accountEmail)
			.credential(passwordEncoder.encode(credential))
			.state(MemberState.TEMP_JOIN)
			.authorities(Collections.singleton(authority))
			.build();

		Long id = memberRepository.save(member).getId();
		log.debug(
			"\n#--------- saved temp join member ---------#\nid : {}\ncredential : {}\naccountEmail : {}\n#--------------------------------#",
			id,
			credential,
			accountEmail
		);
		return id;
	}

	@Transactional
	public MemberJoinResponseDto join(Long id, String accountEmail, String nickname,
		String profileImage, CharacterType character) {

		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new MemberUnexpectedJoinException(ResultCode.ERR_UNEXPECTED_JOIN));

		if (!member.getAccountEmail().equals(accountEmail)) {
			throw new MemberMismatchAccountEmailException(ResultCode.ERR_MISMATCH_ACCOUNT_EMAIL);
		}

		// update member info (completed join)
		String credential = authService.createCredential();
		log.debug("# credential (plain) : {}", credential);

		member.completedJoin(nickname, profileImage, character,
			passwordEncoder.encode(credential));

		log.debug(
			"\n#--------- updated member ---------#\nid : {}\naccountEmail : {}\nnickname : {}\n#--------------------------------#",
			member.getId(),
			accountEmail,
			nickname
		);

		// jwt 발행 (at & rt)
		TokenDto jwt = authService.createToken(member.getNickname(), credential);
		log.debug("\n#--- accessToken : {}\n#--- refreshToken : {}", jwt.accessToken(), jwt.refreshToken());

		MemberJoinResponseDto memberJoinResponseDto = new MemberJoinResponseDto(member, jwt);
		return memberJoinResponseDto;
	}

	// @Transactional
	// public MemberJoinResponseDto join(OauthId oauthId, String accountEmail, String nickname,
	// 	String profileImage, CharacterType character) {
	//
	// 	Optional<Member> findMember = memberRepository.findByAccountEmail(accountEmail);
	// 	if (findMember.isPresent()) {
	// 		throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_ALREADY_JOINED);
	// 	}
	//
	// 	// create member
	// 	Authority authority = Authority.builder()
	// 		.authorityName(Role.MEMBER.getRole())
	// 		.build();
	// 	String credential = authService.createCredential();
	// 	log.debug("# credential (plain) : {}", credential);
	// 	Member member = Member.builder()
	// 		.oauthId(oauthId)
	// 		.accountEmail(accountEmail)
	// 		.credential(passwordEncoder.encode(credential))
	// 		.nickname(nickname)
	// 		.profileImage(profileImage)
	// 		.character(character)
	// 		.authorities(Collections.singleton(authority))
	// 		.build();
	//
	// 	Long id = memberRepository.save(member).getId();
	// 	log.debug(
	// 		"\n#--------- saved member ---------#\nid : {}\ncredential : {}\naccountEmail : {}\n#--------------------------------#",
	// 		id,
	// 		credential,
	// 		accountEmail
	// 	);
	//
	// 	// jwt 발행 (at & rt)
	// 	TokenDto jwt = authService.createToken(member.getNickname(), credential);
	// 	log.debug("\n#--- accessToken : {}\n#--- refreshToken : {}", jwt.accessToken(), jwt.refreshToken());
	//
	// 	MemberJoinResponseDto memberJoinResponseDto = new MemberJoinResponseDto(member, jwt);
	// 	return memberJoinResponseDto;
	// }

	@Transactional
	public Long login(OauthId oauthId, String accountEmail) {

		log.debug("# oauthServerId : {}\noauthServerType : {}\naccountEmail : {}", oauthId.getOauthServerId(),
			oauthId.getOauthServerType(), accountEmail);

		Member findMember = memberRepository.findByOauthIdAndAccountEmail(oauthId, accountEmail)
			.orElseThrow(() -> new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND));

		// 블락, 휴면, 탈퇴 상태인지 확인
		findMember.validateState();

		// 약관 동의 여부 확인

		// 로그인 히스토리 추가

		// 마지막 로그인 일시 업데이트
		findMember.updateLastLoginAt();

		return findMember.getId();
	}

	@Transactional
	public void leave(long memberId) {

		Member findMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND));

		findMember.leaved();

		log.debug("\n#--- leaved member ---#\nid : {}\nnickname : {}\n#--------------------#",
			findMember.getId(),
			findMember.getNickname()
		);
	}
}
