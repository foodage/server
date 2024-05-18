package com.fourdays.foodage.home.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.home.dto.WeeklyReviewRequest;
import com.fourdays.foodage.home.dto.WeeklyReviewResponse;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.service.ReviewService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HomeService {

	private final ReviewService reviewService;

	public HomeService(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	public List<WeeklyReviewResponse> getWeeklyReview(MemberId memberId, WeeklyReviewRequest request) {

		log.debug("# getWeeklyReview(): {} ~ {}", request.getStartDate(),
			request.getEndDate());

		return reviewService.getWeeklyReview(memberId, request);
	}
}
