package com.fourdays.foodage.review.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.model.ReviewModelWithThumbnail;
import com.fourdays.foodage.review.dto.PeriodReviewResponse;
import com.fourdays.foodage.review.dto.RecentReviewResponse;

public interface ReviewCustomRepository {

	Slice<Long> findReviewIds(Long idx, MemberId memberId, Pageable pageable);

	int countByReviewId(MemberId memberId);

	List<ReviewModelWithThumbnail> findReviews(List<Long> ids, MemberId memberId, Pageable pageable);

	List<PeriodReviewResponse> findReviewsByPeriod(MemberId memberId,
		LocalDateTime startDate, LocalDateTime endDate);

	List<ReviewModelWithThumbnail> findReviewsByDate(MemberId memberId, LocalDate date);

	List<RecentReviewResponse> findRecentReviews(MemberId memberId, int limit);
}
