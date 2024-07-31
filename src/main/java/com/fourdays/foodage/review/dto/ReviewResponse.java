package com.fourdays.foodage.review.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.fourdays.foodage.review.domain.model.ReviewModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewResponse {

	private Paging paging;

	private List<ReviewModel> reviews;

	public ReviewResponse(Slice<Long> reviewIds, List<ReviewModel> reviews) {

		Long nextId = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();
		this.paging = new Paging(reviewIds.getPageable().getPageSize(),
			reviewIds.hasNext(), nextId);
		this.reviews = reviews;
	}

	@AllArgsConstructor
	@Getter
	public static class Paging {

		private int size;

		private boolean hasNext;

		private Long nextId;
	}
}
