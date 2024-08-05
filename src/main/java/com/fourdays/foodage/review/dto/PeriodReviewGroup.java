package com.fourdays.foodage.review.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PeriodReviewGroup {

	private String dayOfWeek;

	private int count;

	private List<Long> reviewIds;

	public PeriodReviewGroup(String dayOfWeek, List<Long> reviewIds) {

		this.dayOfWeek = dayOfWeek;
		this.count = reviewIds.size();
		this.reviewIds = reviewIds;
	}
}
