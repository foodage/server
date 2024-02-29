package com.fourdays.foodage.member.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.Role;
import com.fourdays.foodage.jwt.service.AuthService;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;
import com.fourdays.foodage.member.dto.MemberJoinResponseDto;
import com.fourdays.foodage.member.dto.MemberLoginResultDto;
import com.fourdays.foodage.member.exception.MemberDuplicateNicknameException;
import com.fourdays.foodage.member.exception.MemberInvalidOauthServerTypeException;
import com.fourdays.foodage.member.exception.MemberJoinUnexpectedException;
import com.fourdays.foodage.member.exception.MemberJoinedException;
import com.fourdays.foodage.member.exception.MemberMismatchAccountEmailException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.service.OauthQueryService;
import com.fourdays.foodage.oauth.util.OauthServerType;

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
	private final OauthQueryService oauthQueryService;
	private final PasswordEncoder passwordEncoder;

	public MemberCommandService(MemberQueryService memberQueryService, MemberRepository memberRepository,
		AuthService authService, OauthQueryService oauthQueryService, PasswordEncoder passwordEncoder) {
		this.memberQueryService = memberQueryService;
		this.memberRepository = memberRepository;
		this.authService = authService;
		this.oauthQueryService = oauthQueryService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public MemberLoginResultDto tempJoin(OauthId oauthId, String accountEmail) {

		// 사용자 정보 임시 저장 후, 추가 정보 입력받아 join() 메소드에서 update로 회원가입 완료 처리
		Optional<Member> findMember = memberRepository.findByOauthIdAndAccountEmail(oauthId, accountEmail);
		if (findMember.isPresent()) {
			throw new MemberJoinedException(ResultCode.ERR_MEMBER_ALREADY_JOINED);
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

		return new MemberLoginResultDto(member.getNickname(), LoginResult.JOIN_IN_PROGRESS);
	}

	@Transactional
	public MemberJoinResponseDto join(OauthServerType oauthServerType, String accessToken, String accountEmail,
		String nickname, String profileImage, CharacterType character) {

		// 닉네임 존재 여부 확인 (이미 사용중일 시 exception 발생)
		validateUsableNickname(nickname);

		OauthMember oauthMember = null;
		try {
			// 로그인 한 사용자의 oauth 정보 get
			oauthMember = oauthQueryService.getOauthMember(oauthServerType, accessToken);
		} catch (Exception e) {
			throw new MemberInvalidOauthServerTypeException(ResultCode.ERR_INVALID_OAUTH_SERVER_TYPE);
		}

		// 로그인 한 oauth 계정의 이메일이 가입 요청 이메일과 다른 경우
		if (!oauthMember.getAccountEmail().equals(accountEmail)) {
			throw new MemberMismatchAccountEmailException(ResultCode.ERR_MISMATCH_ACCOUNT_EMAIL);
		}

		Member member = memberRepository.findByOauthIdAndAccountEmail(oauthMember.getOauthId(),
				oauthMember.getAccountEmail())
			.orElseThrow(() -> new MemberJoinUnexpectedException(ResultCode.ERR_UNEXPECTED_JOIN));

		// update로 회원가입 완료 처리
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

		return new MemberJoinResponseDto(member, jwt);
	}

	@Transactional
	public MemberLoginResultDto login(OauthId oauthId, String accountEmail) {

		log.debug("# oauthServerId : {}\noauthServerType : {}\naccountEmail : {}", oauthId.getOauthServerId(),
			oauthId.getOauthServerType(), accountEmail);

		Member findMember = memberRepository.findByOauthIdAndAccountEmail(oauthId, accountEmail)
			.orElseThrow(() -> new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND));

		// 가입 진행중인지 확인
		LoginResult loginResult = findMember.getLoginResultByMemberState();

		// 블락, 휴면, 탈퇴 상태인지 확인
		findMember.validateState();

		// 약관 동의 여부 확인

		// 로그인 히스토리 추가

		// 마지막 로그인 일시 업데이트
		findMember.updateLastLoginAt();

		return new MemberLoginResultDto(findMember.getNickname(), loginResult);
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

	//////////////////////////////////////////////////////////////////

	public void validateUsableNickname(String nickname) {

		Long id = memberRepository.findIdByNickname(nickname);
		if (id != null) {
			throw new MemberDuplicateNicknameException(ResultCode.ERR_DUPLICATE_NICKNAME);
		}
	}
}
