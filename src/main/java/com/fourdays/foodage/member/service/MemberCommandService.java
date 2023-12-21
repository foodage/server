package com.fourdays.foodage.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.common.exception.MemberException;
import com.fourdays.foodage.member.domain.Member;
import com.fourdays.foodage.member.domain.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * author         : ebkim, jhjung
 * date           : 2023/10/19
 * description    : db를 업데이트 하거나 상태를 변경하는 작업을 여기에 작성합니다.
 */
@Service
@Slf4j
public class MemberCommandService {

	private final MemberRepository memberRepository;

	public MemberCommandService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void leave(long memberId) {
		Optional<Member> findMember = memberRepository.findById(memberId);
		if (findMember.isEmpty()) {
			throw new MemberException(ResultCode.ERR_MEMBER_NOT_FOUND);
		}

		findMember.get().leaved(findMember.get());

		log.debug("\n#--- leaved member ---#\nid : {}\nnickname : {}\n#--------------------#",
			findMember.get().getId(),
			findMember.get().getNickname()
		);
	}
}
