package com.fourdays.foodage.member.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.jwt.service.AuthUtilService;
import com.fourdays.foodage.member.dto.MemberJoinRequestDto;
import com.fourdays.foodage.member.dto.MemberJoinResponseDto;
import com.fourdays.foodage.member.dto.MemberResponseDto;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.member.service.MemberQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "member")
@Slf4j
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

	@Operation(summary = "사용자 정보 조회 (id 기반)")
	@GetMapping("/member/{id}")
	public ResponseEntity<MemberResponseDto> getMemberInfo(
		@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(memberQueryService.getMember(id));
	}

	@Operation(summary = "사용자 이메일 조회 (id 기반)")
	@GetMapping("/member/account-email/{id}")
	public ResponseEntity<String> getMemberAccountEmail(
		@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(memberQueryService.getAccountEmail(id));
	}

	@Operation(summary = "foodage 서비스 회원가입 완료 요청 (추가 정보 필수)")
	@PostMapping("/member/join")
	public ResponseEntity<MemberJoinResponseDto> join(@RequestBody MemberJoinRequestDto memberCreateRequest) {

		MemberJoinResponseDto memberJoinResponseDto = memberCommandService.join(
			memberCreateRequest.getOauthServerType(), memberCreateRequest.getAccessToken(),
			memberCreateRequest.getAccountEmail(), memberCreateRequest.getNickname(),
			memberCreateRequest.getProfileImage(), memberCreateRequest.getCharacter()
		);

		HttpHeaders httpHeaders = authUtilService.createHeader(memberJoinResponseDto.getJwt());
		return new ResponseEntity<>(memberJoinResponseDto, httpHeaders, HttpStatus.CREATED);
	}

	@Operation(summary = "회원 탈퇴")
	@PutMapping("/member/{id}")
	public ResponseEntity leaveMember(@PathVariable("id") long id) {

		memberCommandService.leave(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
