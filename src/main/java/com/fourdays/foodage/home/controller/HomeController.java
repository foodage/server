package com.fourdays.foodage.home.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.home.service.HomeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "home", description = "메인 홈 화면에서 사용하는 api")
public class HomeController {

	private final HomeService homeService;

	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}
}
