package com.fourdays.foodage.review.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.Review;
import com.fourdays.foodage.review.domain.ReviewCustomRepository;
import com.fourdays.foodage.review.domain.ReviewRepository;
import com.fourdays.foodage.review.domain.model.ReviewModel;
import com.fourdays.foodage.review.domain.model.ReviewModelWithThumbnail;
import com.fourdays.foodage.review.dto.DateReviewResponse;
import com.fourdays.foodage.review.dto.PeriodReviewGroup;
import com.fourdays.foodage.review.dto.PeriodReviewRequest;
import com.fourdays.foodage.review.dto.PeriodReviewResponse;
import com.fourdays.foodage.review.dto.RecentReviewResponse;
import com.fourdays.foodage.review.dto.ReviewResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {

	private final ReviewCustomRepository reviewCustomRepository;
	private final ReviewRepository reviewRepository;

	public ReviewService(ReviewCustomRepository reviewCustomRepository, ReviewRepository reviewRepository) {
		this.reviewCustomRepository = reviewCustomRepository;
		this.reviewRepository = reviewRepository;
	}

	public ReviewModel getReview(final MemberId memberId,
		final Long reviewId) {

		return reviewCustomRepository.findReviewById(memberId, reviewId);
	}

	public ReviewResponse getReviews(final Long idx, final MemberId memberId,
		final Pageable pageable) {

		int totalCount = reviewCustomRepository.countByReviewId(memberId);

		Slice<Long> reviewIds =
			reviewCustomRepository.findReviewIds(idx, memberId, pageable);

		List<ReviewModelWithThumbnail> reviews =
			reviewCustomRepository.findReviews(reviewIds.getContent(), memberId, pageable);

		return new ReviewResponse(reviewIds, reviews, totalCount);
	}

	public DateReviewResponse getReviewsByDate(final MemberId memberId,
		final LocalDate date) {

		log.debug("* getReviewsByDate() : {}", date);

		List<ReviewModelWithThumbnail> reviewModels =
			reviewCustomRepository.findReviewsByDate(memberId, date);

		return new DateReviewResponse(reviewModels, date);
	}

	public List<RecentReviewResponse> getRecentReviews(final MemberId memberId,
		final int limit) {

		log.debug("* getRecentReviews() limit : {}", limit);

		return reviewCustomRepository.findRecentReviews(memberId, limit);
	}

	public Map<LocalDate, PeriodReviewGroup> getReviewsByPeriod(final MemberId memberId,
		final PeriodReviewRequest request) {

		log.debug("* getReviewsByPeriod() : {} ~ {}", request.getStartDate(),
			request.getEndDate());

		List<PeriodReviewResponse> weeklyReviews =
			reviewCustomRepository.findReviewsByPeriod(memberId, request.getStartDate(),
				request.getEndDate());

		// todo: refactoring
		Map<LocalDate, PeriodReviewGroup> response = weeklyReviews.stream()
			.collect(Collectors.groupingBy(PeriodReviewResponse::getCreatedAt,
				TreeMap::new,
				Collectors.collectingAndThen(
					Collectors.toList(),
					list -> {
						String dayOfWeek = list.get(0).getDayOfWeek();
						// String lastEatenFood = list.get(list.size() - 1).getLastEatenFood();
						int count =
							list.stream()
								.map(PeriodReviewResponse::getId)
								.collect(Collectors.toList()).size();

						return new PeriodReviewGroup(dayOfWeek, count);
					}
				)
			));

		return response;
	}

	public Review addReview(Review review) {

		Review addReview = reviewRepository.save(review);

		log.debug("* add review id : {}", addReview.getId());

		return addReview;
	}
}
