package com.fourdays.foodage.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;

import com.fourdays.foodage.jwt.dto.UserTokenDto;
import com.fourdays.foodage.jwt.service.UserService;

@RestController
@RequestMapping("/jwt")
public class UserTokenController {
	private final UserService userService;

	public UserTokenController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public ResponseEntity<UserTokenDto> signup(
		@Valid @RequestBody UserTokenDto userTokenDto
	) {
		return ResponseEntity.ok(userService.signup(userTokenDto));
	}

	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<UserTokenDto> getMyUserInfo(HttpServletRequest request) {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<UserTokenDto> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username));
	}
}
