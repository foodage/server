package com.fourdays.foodage.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.jwt.dto.MemberTokenDto;
import com.fourdays.foodage.jwt.service.MemberTokenService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/jwt")
@Hidden
public class MemberTokenController {
	private final MemberTokenService memberTokenService;

	public MemberTokenController(MemberTokenService memberTokenService) {
		this.memberTokenService = memberTokenService;
	}

	@PostMapping("/signup")
	public ResponseEntity<MemberTokenDto> signup(
		@Valid @RequestBody MemberTokenDto memberTokenDto
	) {
		return ResponseEntity.ok(memberTokenService.signup(memberTokenDto));
	}

	@GetMapping("/member")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<MemberTokenDto> getMyUserInfo(HttpServletRequest request) {
		return ResponseEntity.ok(memberTokenService.getMemberWithAuthorities());
	}

	@GetMapping("/member/{memberName}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<MemberTokenDto> getUserInfo(@PathVariable String memberName) {
		return ResponseEntity.ok(memberTokenService.getMemberWithAuthorities(memberName));
	}
}
