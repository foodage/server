package com.fourdays.foodage.review.dto;

import java.util.List;

import com.fourdays.foodage.review.domain.model.ReviewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewResponse {

	private int totalCount;

	private List<ReviewModel> reviews;

	public ReviewResponse(List<ReviewModel> reviews) {

		this.totalCount = reviews.size();
		this.reviews = reviews;
	}
}
