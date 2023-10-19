package com.fourdays.foodage.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.user.service.UserQueryService;
import com.fourdays.foodage.user.service.dto.UserInfo;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * author         : ebkim, jhjung
 * date           : 2023/10/19
 * description    : db의 변경이 일어나지 않는 단순 조회 api를 여기에 작성합니다.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "user-query")
@Slf4j
public class UserQueryController {

	private final UserQueryService userQueryService;

	public UserQueryController(UserQueryService userQueryService) {
		this.userQueryService = userQueryService;
	}

	@GetMapping("/info/{id}")
	public ResponseEntity<UserInfo> getUserInfo(
		@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(userQueryService.getUserInfo(id));
	}
}
