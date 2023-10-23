package com.fourdays.foodage.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.user.controller.command.UserCreateRequest;
import com.fourdays.foodage.user.service.UserCommandService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * author         : ebkim, jhjung
 * date           : 2023/10/19
 * description    : db를 업데이트 하거나 상태를 변경하는 api를 여기에 작성합니다.
 */
@RestController
@RequestMapping("/user")
@Tag(name = "user-command")
@Slf4j
public class UserCommandController {

	private final UserCommandService userCommandService;

	public UserCommandController(UserCommandService userCommandService) {
		this.userCommandService = userCommandService;
	}

	@PostMapping
	public ResponseEntity createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {

		userCommandService.save(userCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
