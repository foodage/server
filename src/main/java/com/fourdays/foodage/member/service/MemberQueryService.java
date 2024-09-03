package com.fourdays.foodage.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;
import com.fourdays.foodage.member.dto.MemberResponseDto;
import com.fourdays.foodage.member.exception.MemberNotFoundException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.oauth.domain.OauthId;

import lombok.extern.slf4j.Slf4j;

/**
 * author         : ebkim, jhjung
 * date           : 2023/10/19
 * description    : db의 변경이 일어나지 않는 단순 조회 작업을 여기에 작성합니다.
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class MemberQueryService {

	private final MemberRepository memberRepository;

	public MemberQueryService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member findById(final Long id) {
		Member findMember = memberRepository.findById(id)
			.orElseThrow(() -> new MemberNotFoundException(ExceptionInfo.ERR_MEMBER_NOT_FOUND));
		log.debug(
			"findById (param | id : {})\n#--------- find member info ---------#\nid : {}\naccountEmail : {}\noauthServerType : {}\ncredential : {}\nstate : {}\n#------------------------------------#",
			id, findMember.getId(), findMember.getAccountEmail(),
			findMember.getOauthId().getOauthServerType(), findMember.getCredential(), findMember.getState()
		);

		return findMember;
	}

	public Member findByOauthIdAndAccountEmail(final OauthId oauthId, final String accountEmail) {

		Member findMember = memberRepository.findByOauthIdAndAccountEmail(oauthId, accountEmail)
			.orElseThrow(() -> new MemberNotJoinedException(ExceptionInfo.ERR_MEMBER_NOT_FOUND));

		log.debug(
			"findByOauthIdAndAccountEmail (param | oauthId : {} {}, accountEmail : {})\n#--------- find member info ---------#\nid : {}\naccountEmail : {}\noauthServerType : {}\ncredential : {}\nstate : {}\n#------------------------------------#",
			oauthId.getOauthServerType(), oauthId.getOauthServerId(), accountEmail,
			findMember.getId(), findMember.getAccountEmail(), findMember.getOauthId().getOauthServerType(),
			findMember.getCredential(), findMember.getState()
		);

		return findMember;
	}

	public Member findByMemberId(final MemberId memberId) {

		Member findMember = memberRepository.findByOauthIdOauthServerTypeAndAccountEmail(
				memberId.oauthServerType(), memberId.accountEmail())
			.orElseThrow(() -> new MemberNotJoinedException(ExceptionInfo.ERR_MEMBER_NOT_FOUND));

		log.debug(
			"findByOauthServerTypeAndAccountEmail (param | oauthServerType : {}, accountEmail : {})\n#--------- find member info ---------#\nid : {}\naccountEmail : {}\noauthServerType : {}\ncredential : {}\nstate : {}\n#------------------------------------#",
			memberId.oauthServerType(), memberId.accountEmail(), findMember.getId(),
			findMember.getAccountEmail(), findMember.getOauthId().getOauthServerType(),
			findMember.getCredential(), findMember.getState()
		);

		return findMember;
	}

	public String findAccountEmailByMemberId(final MemberId memberId) {

		Member findMember = memberRepository.findByOauthIdOauthServerTypeAndAccountEmail(
			memberId.oauthServerType(), memberId.accountEmail()
		).orElseThrow(() -> new MemberNotFoundException(ExceptionInfo.ERR_MEMBER_NOT_FOUND));
		String findAccountEmail = findMember.getAccountEmail();

		log.debug(
			"findAccountEmailById (param | memberId : {}, {})\n#--------- find account email ---------#\naccountEmail : {}\n#--------------------------------------#",
			memberId.oauthServerType(), memberId.accountEmail(),
			findAccountEmail
		);

		return findAccountEmail;
	}

	public boolean existByNickname(final String nickname) {

		return memberRepository.existsByNickname(nickname);
	}

	public boolean existsByOauthIdAndAccountEmail(final OauthId oauthId, final String accountEmail) {

		return memberRepository.existsByOauthIdAndAccountEmail(oauthId, accountEmail);
	}

	//////////////////////////////////////////////////////////////////

	public MemberResponseDto getMemberById(final Long id) {

		return new MemberResponseDto(findById(id));
	}
}
