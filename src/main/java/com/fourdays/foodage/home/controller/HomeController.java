package com.fourdays.foodage.home.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.home.dto.WeeklyReviewRequest;
import com.fourdays.foodage.home.dto.WeeklyReviewResponse;
import com.fourdays.foodage.home.service.HomeService;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.vo.MemberId;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Home API")
public class HomeController {

	private final HomeService homeService;

	public HomeController(HomeService homeService) {
		this.homeService = homeService;
	}

	@Operation(summary = "이번주 리뷰 작성 목록 조회", hidden = true)
	@GetMapping("/home/weekly-review")
	public ResponseEntity<List<WeeklyReviewResponse>> getWeeklyReview(
		@ParameterObject WeeklyReviewRequest request) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		List<WeeklyReviewResponse> response = homeService.getWeeklyReview(memberId, request);

		return ResponseEntity.ok().body(response);
	}
}
