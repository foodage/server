package com.fourdays.foodage.review.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.enums.ReviewViewType;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.Review;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.dto.DateReviewResponse;
import com.fourdays.foodage.review.dto.PeriodReviewGroup;
import com.fourdays.foodage.review.dto.PeriodReviewRequest;
import com.fourdays.foodage.review.dto.RecentReviewResponse;
import com.fourdays.foodage.review.dto.ReviewResponse;
import com.fourdays.foodage.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

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
	@GetMapping("/reviews/{viewType}")
	public ResponseEntity<Map<LocalDate, PeriodReviewGroup>> getReviewsByPeriod(
		@PathVariable("viewType") ReviewViewType viewType,
		@RequestParam("date") @Nullable YearMonth requestDate) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();

		PeriodReviewRequest period = null;
		switch (viewType) {
			case WEEKLY -> {
				LocalDate now = LocalDate.now();
				period = new PeriodReviewRequest(
					now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)), // startDate
					now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)) // endDate
				);
			}
			case MONTHLY -> {
				YearMonth baseMonth = Objects.requireNonNullElseGet(requestDate, YearMonth::now);
				period = new PeriodReviewRequest(
					baseMonth.atDay(1), // startDate
					baseMonth.atEndOfMonth() // endDate
				);
			}
		}
		Map<LocalDate, PeriodReviewGroup> response =
			reviewService.getReviewsByPeriod(memberId, period);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "리뷰 작성")
	@PostMapping("/review")
	public ResponseEntity<Review> addReview(@RequestBody Review review) {
		Review addReview = reviewService.addReview(review);

		return ResponseEntity.status(HttpStatus.CREATED).body(addReview);
	}
}
