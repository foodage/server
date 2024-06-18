package com.fourdays.foodage.review.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fourdays.foodage.home.dto.PeriodReviewResponse;
import com.fourdays.foodage.home.dto.RecentReviewResponse;
import com.fourdays.foodage.member.vo.MemberId;

public interface ReviewCustomRepository {

	List<PeriodReviewResponse> findWeeklyReviews(MemberId memberId,
		LocalDateTime startDate, LocalDateTime endDate);

	List<RecentReviewResponse> findRecentReviews(MemberId memberId, int limit);
}
