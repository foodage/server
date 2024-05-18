package com.fourdays.foodage.home.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class WeeklyReviewRequest {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	public WeeklyReviewRequest(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public LocalDateTime getStartDate() {
		return startDate != null ? startDate.atStartOfDay() : null;
	}

	public LocalDateTime getEndDate() {
		return endDate != null ? endDate.atStartOfDay() : null;
	}
}
