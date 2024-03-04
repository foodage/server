package com.fourdays.foodage.member.service;

import org.springframework.stereotype.Service;

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
@Slf4j
public class MemberQueryService {

	private final MemberRepository memberRepository;

	public MemberQueryService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public MemberResponseDto getMember(Long id) {

		Member findMember = memberRepository.findById(id)
			.orElseThrow(() -> new MemberNotFoundException(ResultCode.ERR_MEMBER_NOT_FOUND));
		log.debug("getMemberInfo() findMember : {}", findMember);

		return new MemberResponseDto(findMember);
	}

	public Member getMember(OauthServerType oauthServerType, String accountEmail) {

		Member findMember = memberRepository.findByOauthIdOauthServerTypeAndAccountEmail(
				oauthServerType, accountEmail)
			.orElseThrow(() -> new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND));
		return findMember;
	}

	public Member getMember(OauthId oauthId, String accountEmail) {

		Member findMember = memberRepository.findByOauthIdAndAccountEmail(oauthId, accountEmail)
			.orElseThrow(() -> new MemberNotJoinedException(ResultCode.ERR_MEMBER_NOT_FOUND));
		return findMember;
	}

	public String getAccountEmail(Long id) {

		String findAccountEmail = memberRepository.findAccountEmailById(id)
			.orElseThrow(() -> new MemberNotFoundException(ResultCode.ERR_MEMBER_NOT_FOUND));
		log.debug("getMemberInfo() findAccountEmail : {}", findAccountEmail);

		return findAccountEmail;
	}
}
