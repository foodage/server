package com.fourdays.foodage.review.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fourdays.foodage.member.vo.MemberId;
import com.fourdays.foodage.review.domain.ReviewCustomRepository;
import com.fourdays.foodage.review.dto.PeriodReviewGroup;
import com.fourdays.foodage.review.dto.PeriodReviewRequest;
import com.fourdays.foodage.review.dto.PeriodReviewResponse;
import com.fourdays.foodage.review.dto.RecentReviewResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReviewService {

	private final ReviewCustomRepository reviewCustomRepository;

	public ReviewService(ReviewCustomRepository reviewCustomRepository) {
		this.reviewCustomRepository = reviewCustomRepository;
	}

	public Map<LocalDate, PeriodReviewGroup> getReviewsByPeriod(final MemberId memberId,
		final PeriodReviewRequest request) {

		log.debug("# getWeeklyReviews() : {} ~ {}", request.getStartDate(),
			request.getEndDate());

		List<PeriodReviewResponse> weeklyReviews =
			reviewCustomRepository.findWeeklyReviews(memberId, request.getStartDate(),
				request.getEndDate());

		// todo: refactoring
		Map<LocalDate, PeriodReviewGroup> response = weeklyReviews.stream()
			.collect(Collectors.groupingBy(PeriodReviewResponse::getCreatedAt,
				TreeMap::new,
				Collectors.collectingAndThen(
					Collectors.toList(),
					list -> {
						String dayOfWeek = list.get(0).getDayOfWeek();
						String lastEatenFood = list.get(list.size() - 1).getLastEatenFood();
						List<Long> reviewIds =
							list.stream()
								.map(PeriodReviewResponse::getId)
								.collect(Collectors.toList());

						return new PeriodReviewGroup(dayOfWeek, lastEatenFood, reviewIds);
					}
				)
			));
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
