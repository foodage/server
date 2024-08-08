package com.fourdays.foodage.review.dto;

import lombok.Getter;

@Getter
public class PeriodReviewGroup {

	private String dayOfWeek;

	private int count;

	public PeriodReviewGroup(String dayOfWeek, int count) {

		this.dayOfWeek = dayOfWeek;
		this.count = count;
	}
}
