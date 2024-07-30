package com.fourdays.foodage.review.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fourdays.foodage.tag.dto.TagInfo;

import lombok.Getter;

@Getter
public class ReviewModel {

	@JsonIgnore
	private static final int CONTENT_PREVIEW_LENGTH = 30;

	private Long id;

	private String restaurant;

	private String content;

	private float rating;

	private TagInfo tag;

	private List<ReviewImageModel> thumbnails;

	public ReviewModel(Long id, String restaurant, String content, Float rating,
		String name, String bgColor, String textColor,
		List<ReviewImageModel> thumbnails) {

		this.id = id;
		this.restaurant = restaurant;
		this.content = content.substring(0, CONTENT_PREVIEW_LENGTH);
		this.rating = rating;
		this.thumbnails = thumbnails;
		this.tag = new TagInfo(name, bgColor, textColor);
	}
}
