package com.fourdays.foodage.member.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.jwt.service.AuthUtilService;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.dto.MemberJoinRequestDto;
import com.fourdays.foodage.member.dto.MemberJoinResponseDto;
import com.fourdays.foodage.member.dto.MemberResponseDto;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.vo.MemberId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "회원 API", description = "회원 조회, 가입, 탈퇴 등 회원 관련 작업 수행")
public class MemberController {

	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;
	private final AuthUtilService authUtilService;

	public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService,
		AuthUtilService authUtilService) {
		this.memberCommandService = memberCommandService;
		this.memberQueryService = memberQueryService;
		this.authUtilService = authUtilService;
	}

	@Operation(summary = "사용자 상세 정보 조회")
	@GetMapping("/member/detail")
	public ResponseEntity<MemberResponseDto> getMemberInfo(Authentication authentication) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		MemberResponseDto memberResponseDto = new MemberResponseDto(
			memberQueryService.findByMemberId(memberId));

		return ResponseEntity.status(HttpStatus.OK)
			.body(memberResponseDto);
	}

	@Operation(summary = "사용자 이메일 조회 (id 기반)")
	@GetMapping("/member/account-email")
	public ResponseEntity<String> getMemberAccountEmail(Authentication authentication) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();

		return ResponseEntity.status(HttpStatus.OK)
			.body(memberQueryService.findAccountEmailByMemberId(memberId));
	}

	@Operation(summary = "foodage 서비스 회원가입 완료 요청 (추가 정보 필수)")
	@PostMapping("/member/join")
	public ResponseEntity<MemberJoinResponseDto> join(@RequestBody MemberJoinRequestDto memberCreateRequest) {

		MemberJoinResponseDto memberJoinResponseDto = memberCommandService.join(
			memberCreateRequest.oauthServerType(), memberCreateRequest.accessToken(),
			memberCreateRequest.nickname(), memberCreateRequest.character()
		);

		HttpHeaders httpHeaders = authUtilService.createHeader(memberJoinResponseDto.jwt());
		return new ResponseEntity<>(memberJoinResponseDto, httpHeaders, HttpStatus.CREATED);
	}

	@Operation(summary = "회원 탈퇴")
	@PutMapping("/member/leave")
	public ResponseEntity leaveMember(Authentication authentication) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		memberCommandService.leave(memberId);

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
