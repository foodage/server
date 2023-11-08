package com.fourdays.foodage.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HelloController {

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("hello");
	}

	@GetMapping("/hellotest")
	public ResponseEntity<String> hellotest() {
		return ResponseEntity.ok("hellotest");
	}
}
