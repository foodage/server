package com.fourdays.foodage.review.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.domain.model.ReviewModelWithThumbnail;
import com.fourdays.foodage.review.dto.PeriodReviewResponse;
import com.fourdays.foodage.review.dto.RecentReviewResponse;

public interface ReviewCustomRepository {

	// todo : repository에서는 model만 반환하도록 처리
	// by ~
	ReviewModel findReviewById(MemberId memberId, Long reviewId);

	List<ReviewModelWithThumbnail> findReviewsByDate(MemberId memberId, LocalDate date);

	List<PeriodReviewResponse> findReviewsByPeriod(MemberId memberId,
		LocalDateTime startDate, LocalDateTime endDate);

	// list
	List<ReviewModelWithThumbnail> findReviews(List<Long> ids, MemberId memberId, Pageable pageable);

	List<RecentReviewResponse> findRecentReviews(MemberId memberId, int limit);

	// reviewId
	Slice<Long> findReviewIds(Long idx, MemberId memberId, Pageable pageable);

	int countByReviewId(MemberId memberId);
}
