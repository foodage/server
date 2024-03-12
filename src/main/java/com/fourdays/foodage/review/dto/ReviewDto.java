package com.fourdays.foodage.review.dto;

import java.time.LocalDateTime;

import com.fourdays.foodage.review.domain.Review;

public record ReviewDto(
	String place,
	String menu,
	Integer price,
	Integer rating,
	String review,
	String tag,
	String image,
	LocalDateTime date
) {

	public ReviewDto(Review review) {
		this(review.getPlace(), review.getMenu(), review.getPrice(), review.getRating(),
			review.getReview(), review.getTag(), review.getImage(), review.getDate());
	}

}
