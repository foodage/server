package com.fourdays.foodage.home.controller;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.home.dto.RecentReviewResponse;
import com.fourdays.foodage.home.dto.WeeklyReviewRequest;
import com.fourdays.foodage.home.dto.WeeklyReviewResponse;
import com.fourdays.foodage.home.service.HomeService;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "Home API")
public class HomeController {

	private final HomeService homeService;
	private final ReviewService reviewService;

	public HomeController(HomeService homeService, ReviewService reviewService) {
		this.homeService = homeService;
		this.reviewService = reviewService;
	}

	@Operation(summary = "이번주 작성한 리뷰 목록 조회")
	@GetMapping("/home/weekly-review")
	public ResponseEntity<List<WeeklyReviewResponse>> getWeeklyReviews(
		@ParameterObject WeeklyReviewRequest request) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		List<WeeklyReviewResponse> response = reviewService.getWeeklyReviews(memberId, request);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "최근 작성한 리뷰 목록 조회")
	@GetMapping("/home/recent-review")
	public ResponseEntity<List<RecentReviewResponse>> getRecentReviews(
		@RequestParam("limit") int limit) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		List<RecentReviewResponse> response = reviewService.getRecentReviews(memberId, limit);

		return ResponseEntity.ok().body(response);
	}
}
