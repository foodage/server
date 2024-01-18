package com.fourdays.foodage.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;
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

	public MemberCommandService(MemberQueryService memberQueryService, MemberRepository memberRepository) {
		this.memberQueryService = memberQueryService;
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void join(OauthId oauthId, String accountEmail, String nickname, String profileUrl) {

		Optional<Member> findMember = memberRepository.findByAccountEmail(accountEmail);
		if (findMember.isPresent())
			throw new MemberNotJoinedException(ResultCode.ERR_MEMBER_ALREADY_JOINED);

		Member member = Member.builder()
			.oauthId(oauthId)
			.accountEmail(accountEmail)
			.nickname(nickname)
			.profileUrl(profileUrl)
			.build();
		Long id = memberRepository.save(member).getId();

		log.debug("\n#--- saved member ---#\nid : {}\naccountEmail : {}\n#--------------------#",
			id,
			accountEmail
		);
	}

	@Transactional
	public void findMemberByIdentifier(OauthId oauthId, String accountEmail) {
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

		// jwt 발행 및 반환
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
