package com.fourdays.foodage.review.controller;

import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.Review;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.dto.*;
import com.fourdays.foodage.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@Tag(name = "review", description = "리뷰 관련 api")
public class ReviewController {

	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Operation(summary = "리뷰 상세 조회")
	@GetMapping("/review/{id}")
	public ResponseEntity<ReviewModel> getReview(@PathVariable("id") @NotNull Long reviewId) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		ReviewModel response = reviewService.getReview(memberId, reviewId);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "전체 리뷰 목록 조회")
	@GetMapping("/reviews")
	public ResponseEntity<ReviewResponse> getReviews(
		@RequestParam("idx") @Nullable Long lastReviewId,
		@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
	) {
		MemberId memberId = SecurityUtil.getCurrentMemberId();
		ReviewResponse response =
			reviewService.getReviews(lastReviewId, memberId, pageable);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "특정일의 리뷰 목록 조회")
	@GetMapping("/reviews/date")
	public ResponseEntity<DateReviewResponse> getReviewsByDate(
		@RequestParam("on") LocalDate date) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		DateReviewResponse response = reviewService.getReviewsByDate(memberId, date);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "최근 작성한 리뷰 목록 조회")
	@GetMapping("/reviews/recent")
	public ResponseEntity<List<RecentReviewResponse>> getRecentReviews(
		@RequestParam("limit") int limit) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		List<RecentReviewResponse> response = reviewService.getRecentReviews(memberId, limit);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "캘린더 내 리뷰 조회")
	@GetMapping("/reviews/calendar")
	public ResponseEntity<Map<LocalDate, PeriodReviewGroup>> getReviewsByPeriod(
		@RequestParam("on") @Nullable YearMonth requestDate) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();

		YearMonth baseMonth = Objects.requireNonNullElseGet(requestDate, YearMonth::now);
		PeriodReviewRequest period = new PeriodReviewRequest(
			baseMonth.atDay(1), // startDate
			baseMonth.atEndOfMonth() // endDate
		);

		Map<LocalDate, PeriodReviewGroup> response =
			reviewService.getReviewsByPeriod(memberId, period);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "리뷰 작성")
	@PostMapping("/review")
	public ResponseEntity<Review> createReview(@RequestBody CreateReviewRequestDto request) {
		MemberId memberId = SecurityUtil.getCurrentMemberId();

		Review createdReview = reviewService.createReview(request, memberId);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
	}
}
