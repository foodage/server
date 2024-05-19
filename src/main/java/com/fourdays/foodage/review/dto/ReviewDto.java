package com.fourdays.foodage.review.dto;

import java.time.LocalDateTime;

import com.fourdays.foodage.review.domain.Review;

public record ReviewDto(

	String restaurant,
	String address,
	String menu,
	Integer price,
	Integer rating,
	String contents,
	int tagId,
	int thumbnailId,
	// List<String> images,
	// String lastEatenFood,
	LocalDateTime date
) {

	public ReviewDto(Review review) {
		this(review.getRestaurant(), review.getAddress(), review.getMenu(),
			review.getPrice(), review.getRating(), review.getContents(),
			review.getTagId(), review.getThumbnailId(), review.getCreatedAt());
	}
}
