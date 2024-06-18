package com.fourdays.foodage.home.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class PeriodReviewGroup {

	private String dayOfWeek;

	private String lastEatenFood;

	private int count;

	private List<Long> reviewIds;

	public PeriodReviewGroup(String dayOfWeek, String lastEatenFood, List<Long> reviewIds) {

		this.dayOfWeek = dayOfWeek;
		this.lastEatenFood = lastEatenFood;
		this.count = reviewIds.size();
		this.reviewIds = reviewIds;
	}
}
