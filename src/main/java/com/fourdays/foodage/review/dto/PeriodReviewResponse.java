package com.fourdays.foodage.review.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;

@Getter
public class PeriodReviewResponse {

	@JsonIgnore
	private LocalDate createdAt;

	private Long id;

	private String dayOfWeek;

	private String lastEatenFood;

	public PeriodReviewResponse(Long id, LocalDateTime createdAt, String lastEatenFood) {

		this.createdAt = createdAt.toLocalDate();
		this.id = id;
		this.dayOfWeek = createdAt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		this.lastEatenFood = lastEatenFood;
	}
}
