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

import com.fourdays.foodage.jwt.enums.JwtType;
import com.fourdays.foodage.jwt.handler.JwtFilter;
import com.fourdays.foodage.member.dto.MemberCreateRequestDto;
import com.fourdays.foodage.member.dto.MemberJoinResponseDto;
import com.fourdays.foodage.member.dto.MemberResponseDto;
import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.member.service.MemberQueryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Tag(name = "member")
@Slf4j
public class MemberController {

	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;

	public MemberController(MemberCommandService memberCommandService, MemberQueryService memberQueryService) {
		this.memberCommandService = memberCommandService;
		this.memberQueryService = memberQueryService;
	}

	@GetMapping("/member/{id}")
	public ResponseEntity<MemberResponseDto> getMemberInfo(
		@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(memberQueryService.getMemberById(id));
	}

	@PostMapping("/member/join")
	public ResponseEntity<MemberJoinResponseDto> join(@RequestBody MemberCreateRequestDto memberCreateRequest) {

		MemberJoinResponseDto memberJoinResponseDto = memberCommandService.join(
			memberCreateRequest.getId(), memberCreateRequest.getAccountEmail(),
			memberCreateRequest.getNickname(), memberCreateRequest.getProfileImage(),
			memberCreateRequest.getCharacter());

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER,
			"Bearer " + memberJoinResponseDto.getJwt().accessToken());
		httpHeaders.add(JwtType.REFRESH_TOKEN.getHeaderName(),
			"Bearer " + memberJoinResponseDto.getJwt().refreshToken());

		return new ResponseEntity<>(memberJoinResponseDto, httpHeaders, HttpStatus.CREATED);
	}

	@PutMapping("/member/{id}")
	public ResponseEntity leaveMember(@PathVariable("id") long id) {

		memberCommandService.leave(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
