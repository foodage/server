package com.fourdays.foodage.review.domain.model;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ReviewModel {

	private Long id;

	private String place;

	private String menu;

	private Integer price;

	private Integer rating;

	private String review;

	private String tag;

	private String thumbnail;

	private LocalDateTime createdAt;
}
