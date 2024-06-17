package com.fourdays.foodage.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.home.dto.PeriodReviewRequest;
import com.fourdays.foodage.home.dto.RecentReviewResponse;
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

	public List<WeeklyReviewResponse> getReviewsByPeriod(final MemberId memberId,
		final PeriodReviewRequest request) {

		log.debug("# getWeeklyReviews() : {} ~ {}", request.getStartDate(),
			request.getEndDate());

		List<WeeklyReviewResponse> response =
			reviewCustomRepository.findWeeklyReview(memberId, request.getStartDate(),
				request.getEndDate());
		return response;
	}

	public List<RecentReviewResponse> getRecentReviews(final MemberId memberId,
		final int limit) {

		log.debug("# getRecentReviews() limit : {}", limit);

		List<RecentReviewResponse> response =
			reviewCustomRepository.findRecentReviews(memberId, limit);
		return response;
	}
}
