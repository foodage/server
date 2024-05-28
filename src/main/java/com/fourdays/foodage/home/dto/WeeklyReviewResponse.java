package com.fourdays.foodage.home.dto;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import lombok.Getter;

@Getter
public class WeeklyReviewResponse {

	private Long id;

	private LocalDateTime createdAt;

	private String dayOfWeek;

	private String lastEatenFood;

	public WeeklyReviewResponse(Long id, LocalDateTime createdAt, String lastEatenFood) {
		this.id = id;
		this.createdAt = createdAt;
		this.dayOfWeek = createdAt.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN);
		this.lastEatenFood = lastEatenFood;
	}
}
