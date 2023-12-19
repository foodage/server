package com.fourdays.foodage.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.user.controller.command.UserCreateRequest;
import com.fourdays.foodage.user.service.UserCommandService;
import com.fourdays.foodage.user.service.UserQueryService;
import com.fourdays.foodage.user.service.dto.UserInfo;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Tag(name = "user")
@Slf4j
public class UserController {

	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;

	public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
		this.userCommandService = userCommandService;
		this.userQueryService = userQueryService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserInfo> getUserInfo(
		@PathVariable Long id) {

		return ResponseEntity.status(HttpStatus.OK).body(userQueryService.getUserInfo(id));
	}

	@PostMapping
	public ResponseEntity createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {

		userCommandService.save(userCreateRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity leaveUser(@PathVariable("id") long id) {

		userCommandService.leave(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
