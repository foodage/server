package com.fourdays.foodage.review.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fourdays.foodage.review.domain.model.ReviewModelWithThumbnail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DateReviewResponse {

	private int totalCount;

	private DateInfo dateRange;

	private List<ReviewModelWithThumbnail> reviews;

	public DateReviewResponse(List<ReviewModelWithThumbnail> reviews, LocalDate dateRange) {

		this.dateRange = new DateInfo(dateRange.atStartOfDay(), dateRange.atTime(LocalTime.MAX));
		this.totalCount = reviews.size();
		this.reviews = reviews;
	}

	@AllArgsConstructor
	@Getter
	public static class DateInfo {

		private LocalDateTime start;

		private LocalDateTime end;
	}
}
