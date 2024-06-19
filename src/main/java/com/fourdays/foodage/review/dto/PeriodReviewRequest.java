package com.fourdays.foodage.review.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class PeriodReviewRequest {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime endDate;

	public PeriodReviewRequest(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate.atStartOfDay();
		this.endDate = endDate.atTime(LocalTime.MAX);
	}

	public LocalDateTime getStartDate() {
		return startDate != null ? startDate : null;
	}

	public LocalDateTime getEndDate() {
		return endDate != null ? endDate : null;
	}
}
