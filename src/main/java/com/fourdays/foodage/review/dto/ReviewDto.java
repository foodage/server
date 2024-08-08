package com.fourdays.foodage.review.dto;

import java.time.LocalDateTime;

import com.fourdays.foodage.review.domain.Review;

public record ReviewDto(

	String restaurant,
	String address,
	float rating,
	String contents,
	Long thumbnailId,
	LocalDateTime date
) {

	public ReviewDto(Review review) {
		this(review.getRestaurant(), review.getAddress(), review.getRating(),
			review.getContents(), review.getThumbnailId(), review.getCreatedAt());
	}
}
