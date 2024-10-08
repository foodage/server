package com.fourdays.foodage.member.service;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.CharacterType;
import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.enums.MemberState;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;
import com.fourdays.foodage.jwt.domain.Authority;
import com.fourdays.foodage.jwt.dto.TokenDto;
import com.fourdays.foodage.jwt.enums.Role;
import com.fourdays.foodage.jwt.service.AuthService;
import com.fourdays.foodage.member.domain.LeaveRequestRepository;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;
import com.fourdays.foodage.member.dto.MemberJoinResponseDto;
import com.fourdays.foodage.member.dto.MemberLeaveResponseDto;
import com.fourdays.foodage.member.dto.MemberLoginResultDto;
import com.fourdays.foodage.member.dto.MemberProfileUpdateRequestDto;
import com.fourdays.foodage.member.exception.MemberDuplicateNicknameException;
import com.fourdays.foodage.member.exception.MemberInvalidOauthServerTypeException;
import com.fourdays.foodage.member.exception.MemberInvalidStateException;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.domain.OauthMember;
import com.fourdays.foodage.oauth.service.OauthService;
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
	private final LeaveRequestRepository leaveRequestRepository;
	private final AuthService authService;
	private final OauthService oauthService;
	private final PasswordEncoder passwordEncoder;

	public MemberCommandService(MemberQueryService memberQueryService, MemberRepository memberRepository,
		LeaveRequestRepository leaveRequestRepository, AuthService authService, OauthService oauthService,
		PasswordEncoder passwordEncoder) {
		this.memberQueryService = memberQueryService;
		this.memberRepository = memberRepository;
		this.leaveRequestRepository = leaveRequestRepository;
		this.authService = authService;
		this.oauthService = oauthService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public MemberLoginResultDto login(final OauthId oauthId, final String accountEmail) {

		// 미가입 유저일 경우, login이 아닌 임시 회원가입 처리
		if (notJoined(oauthId, accountEmail)) {
			return tempJoin(oauthId, accountEmail);
		}

		Member findMember = memberQueryService.findByOauthIdAndAccountEmail(oauthId, accountEmail);
		LoginResult loginResult = findMember.getLoginResultByMemberState();
		if (loginResult == LoginResult.FAILED) { // DORMANT, LEAVE, BLOCK state -> INVALID
			throw new MemberInvalidStateException(ExceptionInfo.ERR_MEMBER_INVALID_STATE, loginResult);
		}

		// 약관 동의 여부 확인

		// 로그인 히스토리 추가

		// credential 교체 (새로 로그인했으므로)
		String credential = authService.updateCredential(oauthId, accountEmail);

		// 마지막 로그인 일시 업데이트
		findMember.updateLastLoginAt();

		return new MemberLoginResultDto(findMember.getOauthId(), findMember.getNickname(),
			findMember.getAccountEmail(), loginResult,
			credential);
	}

	@Transactional
	public MemberLoginResultDto tempJoin(final OauthId oauthId, final String accountEmail) {

		// 사용자 정보 임시 저장
		// 이후 사용자의 추가 정보 입력받아 update 쿼리로 회원가입 완료 처리함

		// 임시 회원가입 정보 생성
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
			"\n#--------- saved temp join member ---------#\nid : {}\ncredential : {}\naccountEmail : {}\n#------------------------------------------#",
			id,
			credential,
			accountEmail
		);

		return new MemberLoginResultDto(member.getOauthId(), member.getNickname(),
			member.getAccountEmail(), LoginResult.JOIN_IN_PROGRESS,
			credential);
	}

	@Transactional
	public MemberJoinResponseDto join(final OauthServerType oauthServerType,
		final String accessToken, final String nickname,
		final CharacterType character) {

		//////////////////// validate ////////////////////
		// 로그인한 사용자의 oauth 정보 get
		OauthMember oauthMember = null;
		try {
			oauthMember = oauthService.getOauthMemberByAccessToken(oauthServerType, accessToken);
		} catch (Exception e) {
			throw new MemberInvalidOauthServerTypeException(ExceptionInfo.ERR_NOT_FOUND_OAUTH_MEMBER);
		}

		// oauth 로그인을 완료하지 않은 사용자일 경우 (temp_join 상태가 아닐 경우)
		Member member = memberQueryService.findByOauthIdAndAccountEmail(oauthMember.getOauthId(),
			oauthMember.getAccountEmail());

		// 사용자가 임시 가입 상태가 맞는지 확인
		member.validateMemberIsTempJoin();

		// 이미 사용중인 닉네임인지 확인 (닉네임 중복 불가능)
		validateNicknameIsDuplicate(nickname);

		//////////////////// 회원가입 완료 처리 (update query) ////////////////////
		String credential = authService.createCredential();
		log.debug("# credential (plain) : {}", credential);

		member.completedJoin(nickname, character,
			passwordEncoder.encode(credential));

		log.debug(
			"\n#--------- updated member ---------#\nid : {}\naccountEmail : {}\nnickname : {}\n#--------------------------------#",
			member.getId(),
			member.getAccountEmail(),
			nickname
		);

		// jwt 발행 (at & rt)
		TokenDto jwt = authService.createToken(member.getOauthId().getOauthServerType(),
			member.getAccountEmail(), credential);
		log.debug("\n#--- accessToken : {}\n#--- refreshToken : {}", jwt.accessToken(), jwt.refreshToken());

		return new MemberJoinResponseDto(member, jwt);
	}

	@Transactional
	public MemberLeaveResponseDto leave(final MemberId memberId) {

		Member findMember = memberQueryService.findByMemberId(memberId);

		// 탈퇴 요청 진행 중인 사용자인지 확인
		// * 정책 : 탈퇴 후 30일 이내 계정 복구 요청 시 복구 가능 / 30일 이후 탈퇴 완료 처리
		if (findMember.getState() == MemberState.PENDING_LEAVE) {
			log.warn("@ member has already requested leave!");
			// message ex: 2024-09-04 AM 11:33에 탈퇴를 요청하신 내역이 있어요.
			return new MemberLeaveResponseDto(true,
				findMember.getLeaveRequestedAt());
		}

		findMember.approveLeaveRequest();

		/*
		 * redis에 탈퇴 요청 30일간 저장
		 * expired 되면 key expired listener에 의해 실제 탈퇴 처리됨 (completeLeave)
		 * 탈퇴 요청으로부터 30일 이내에는 계정 복구 가능
		 */
		leaveRequestRepository.save(memberId);

		// message ex: 2024-09-04 AM 11:33 회원님의 탈퇴 요청이 처리되었어요.
		return new MemberLeaveResponseDto(false,
			LocalDateTime.now());
	}

	/*
	 * 회원 탈퇴 요청으로부터 30일 경과 시, redis key expired event를 통해
	 * 실제 회원 탈퇴가 진행됨 (soft delete)
	 */
	@Transactional
	public void completeLeave(final MemberId memberId) throws FoodageException {

		Member findMember = null;
		try {
			findMember = memberQueryService.findByMemberId(memberId);
		} catch (Exception e) {
			throw new FoodageException(ExceptionInfo.ERR_MEMBER_LEAVE_FAILED);
		}
		findMember.completeLeave();

		// todo: 사용자와 연관된 모든 데이터 삭제
	}

	@Transactional
	public void restore(final MemberId memberId) {

		Member findMember = memberQueryService.findByMemberId(memberId);

		findMember.cancelLeaveRequest();
		leaveRequestRepository.delete(memberId);
	}

	//////////////////////////////////////////////////////////////////

	private void validateNicknameIsDuplicate(final String nickname) {

		boolean isExist = memberQueryService.existByNickname(nickname);
		if (isExist) {
			throw new MemberDuplicateNicknameException(ExceptionInfo.ERR_DUPLICATE_NICKNAME);
		}
	}

	private boolean notJoined(final OauthId oauthId, final String accountEmail) {

		boolean isJoined = memberQueryService.existsByOauthIdAndAccountEmail(oauthId, accountEmail);
		return !isJoined;
	}

	//////////////////////////////////////////////////////////////////

	@Transactional
	public void updateMemberProfile(final MemberId memberId,
		final MemberProfileUpdateRequestDto request) {

		Member findMember = memberQueryService.findByMemberId(memberId);
		findMember.updateProfile(request.characterType(), request.nickname());
	}
}
