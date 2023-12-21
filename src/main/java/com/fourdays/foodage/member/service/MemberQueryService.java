package com.fourdays.foodage.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.common.exception.MemberException;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.repository.MemberRepository;
import com.fourdays.foodage.member.service.dto.MemberInfo;

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

	public MemberInfo getMemberInfo(Long id) {
		Optional<Member> findMember = memberRepository.findById(id);
		log.debug("getMemberInfo() findMember : {}", findMember.get());
		if (findMember.isEmpty()) {
			throw new MemberException(ResultCode.ERR_MEMBER_NOT_FOUND);
		}
		return new MemberInfo(findMember.get());
	}
}
