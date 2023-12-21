package com.fourdays.foodage.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.member.service.MemberCommandService;
import com.fourdays.foodage.member.service.MemberQueryService;
import com.fourdays.foodage.member.service.dto.MemberInfo;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
// @RequestMapping("/member")
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
	public ResponseEntity<MemberInfo> getMemberInfo(
		@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(memberQueryService.getMemberInfo(id));
	}

	@PutMapping("/member/{id}")
	public ResponseEntity leaveMember(@PathVariable("id") long id) {

		memberCommandService.leave(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
