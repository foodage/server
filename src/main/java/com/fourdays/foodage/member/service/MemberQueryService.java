package com.fourdays.foodage.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.MemberRepository;
import com.fourdays.foodage.member.dto.MemberResponseDto;
import com.fourdays.foodage.member.exception.MemberNotFoundException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.oauth.domain.OauthId;
import com.fourdays.foodage.oauth.util.OauthServerType;

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

	public Member findById(Long id) {
		Member findMember = memberRepository.findById(id)
			.orElseThrow(() -> new MemberNotFoundException(ResultCode.ERR_MEMBER_NOT_FOUND));
		log.debug(
			"findById (param | id : {})\n#--------- member info ---------#\nid : {}\naccountEmail : {}\noauthServerType : {}\ncredential : {}\nstate : {}\n#--------------------------------#",
			id, findMember.getId(), findMember.getAccountEmail(),
			findMember.getOauthId().getOauthServerType(), findMember.getCredential(), findMember.getState()
		);

		return findMember;
	}

	public Member findByOauthServerTypeAndAccountEmail(OauthServerType oauthServerType,
		String accountEmail) {
		Member findMember = memberRepository.findByOauthIdOauthServerTypeAndAccountEmail(
				oauthServerType, accountEmail)
			.orElseThrow(() -> new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND));
		log.debug(
			"findByOauthServerTypeAndAccountEmail (param | oauthServerType : {}, accountEmail : {})\n#--------- member info ---------#\nid : {}\naccountEmail : {}\noauthServerType : {}\ncredential : {}\nstate : {}\n#--------------------------------#",
			oauthServerType, accountEmail, findMember.getId(),
			findMember.getAccountEmail(), findMember.getOauthId().getOauthServerType(),
			findMember.getCredential(), findMember.getState()
		);

		return findMember;
	}

	public Member findByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail) {
		Member findMember = memberRepository.findByOauthIdAndAccountEmail(oauthId, accountEmail)
			.orElseThrow(() -> new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND));
		log.debug(
			"findByOauthIdAndAccountEmail (param | oauthId : {} {}, accountEmail : {})\n#--------- member info ---------#\nid : {}\naccountEmail : {}\noauthServerType : {}\ncredential : {}\nstate : {}\n#--------------------------------#",
			oauthId.getOauthServerType(), oauthId.getOauthServerId(), accountEmail,
			findMember.getId(), findMember.getAccountEmail(), findMember.getOauthId().getOauthServerType(),
			findMember.getCredential(), findMember.getState()
		);

		return findMember;
	}

	public String findAccountEmailById(Long id) {
		String findAccountEmail = memberRepository.findAccountEmailById(id)
			.orElseThrow(() -> new MemberNotFoundException(ResultCode.ERR_MEMBER_NOT_FOUND));
		log.debug(
			"findAccountEmailById (param | id : {})\n#--------- account email ---------#\naccountEmail : {}\n#--------------------------------#",
			id, findAccountEmail
		);

		return findAccountEmail;
	}

	public boolean existByNickname(String nickname) {

		return memberRepository.existsByNickname(nickname);
	}

	public boolean existsByOauthIdAndAccountEmail(OauthId oauthId, String accountEmail) {

		return memberRepository.existsByOauthIdAndAccountEmail(oauthId, accountEmail);
	}

	//////////////////////////////////////////////////////////////////

	public MemberResponseDto getMemberById(Long id) {

		return new MemberResponseDto(findById(id));
	}
}
