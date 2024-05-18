package com.fourdays.foodage.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.home.dto.WeeklyReviewRequest;
import com.fourdays.foodage.home.dto.WeeklyReviewResponse;
import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.ReviewCustomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {

	private final ReviewCustomRepository reviewCustomRepository;

	public ReviewService(ReviewCustomRepository reviewCustomRepository) {
		this.reviewCustomRepository = reviewCustomRepository;
	}

	public List<WeeklyReviewResponse> getWeeklyReview(MemberId memberId, WeeklyReviewRequest request) {

		List<WeeklyReviewResponse> response =
			reviewCustomRepository.findWeeklyReview(memberId, request.getStartDate(),
				request.getEndDate());

		return response;
	}
}
