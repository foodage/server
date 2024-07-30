package com.fourdays.foodage.review.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.dto.PeriodReviewResponse;
import com.fourdays.foodage.review.dto.RecentReviewResponse;

public interface ReviewCustomRepository {

	List<ReviewModel> findReviews(MemberId memberId);

	List<PeriodReviewResponse> findReviewsByPeriod(MemberId memberId,
		LocalDateTime startDate, LocalDateTime endDate);

	List<ReviewModel> findReviewsByDate(MemberId memberId, LocalDate date);

	List<RecentReviewResponse> findRecentReviews(MemberId memberId, int limit);
}
