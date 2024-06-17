package com.fourdays.foodage.home.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fourdays.foodage.common.enums.ReviewViewType;
import com.fourdays.foodage.home.dto.PeriodReviewRequest;
import com.fourdays.foodage.home.dto.RecentReviewResponse;
import com.fourdays.foodage.home.dto.TagUsageRankResponse;
import com.fourdays.foodage.home.dto.WeeklyReviewResponse;
import com.fourdays.foodage.home.service.HomeService;
import com.fourdays.foodage.jwt.util.SecurityUtil;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.service.ReviewService;
import com.fourdays.foodage.tag.service.TagService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Tag(name = "home", description = "메인 홈 화면에서 사용하는 api")
public class HomeController {

	private final HomeService homeService;

	private final ReviewService reviewService;

	private final TagService tagService;

	public HomeController(HomeService homeService, ReviewService reviewService, TagService tagService) {
		this.homeService = homeService;
		this.reviewService = reviewService;
		this.tagService = tagService;
	}

	@Operation(summary = "캘린더 내 리뷰 조회")
	@GetMapping("/home/reviews/{viewType}")
	public ResponseEntity<List<WeeklyReviewResponse>> getReviewsByPeriod(
		@PathVariable("viewType") final String viewType) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		ReviewViewType viewType_ = ReviewViewType.of(viewType);

		PeriodReviewRequest period = null;
		switch (viewType_) {
			case WEEKLY -> {
				LocalDate now = LocalDate.now();
				period = new PeriodReviewRequest(
					now.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)), // startDate
					now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)) // endDate
				);
			}
			case MONTHLY -> {
				YearMonth currentMonth = YearMonth.now();
				period = new PeriodReviewRequest(
					currentMonth.atDay(1), // startDate
					currentMonth.atEndOfMonth()
				); // endDate
			}
		}
		List<WeeklyReviewResponse> response = reviewService.getReviewsByPeriod(memberId, period);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "최근 작성한 리뷰 목록 조회")
	@GetMapping("/home/reviews/recent")
	public ResponseEntity<List<RecentReviewResponse>> getRecentReviews(
		@RequestParam("limit") int limit) {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		List<RecentReviewResponse> response = reviewService.getRecentReviews(memberId, limit);

		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "태그 사용 순위 조회")
	@GetMapping("/home/tag/usage-rank")
	public ResponseEntity<List<TagUsageRankResponse>> getTagUsageRank() {

		MemberId memberId = SecurityUtil.getCurrentMemberId();
		List<TagUsageRankResponse> response = tagService.getTagUsageRank(memberId);

		return ResponseEntity.ok().body(response);
	}
}
