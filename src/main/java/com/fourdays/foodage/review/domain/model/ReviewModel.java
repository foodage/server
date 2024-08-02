package com.fourdays.foodage.review.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fourdays.foodage.tag.domain.model.TagModel;

import lombok.Getter;

@Getter
public class ReviewModel {

	@JsonIgnore
	private static final int CONTENT_PREVIEW_LENGTH = 30;

	private Long id;

	private String restaurant;

	private String content;

	private float rating;

	private LocalDateTime createdAt;

	private List<TagModel> tags;

	private List<ReviewImageModel> images;

	public ReviewModel(Long id, String restaurant, String content, Float rating,
		LocalDateTime createdAt, List<TagModel> tags, List<ReviewImageModel> images) {

		this.id = id;
		this.restaurant = restaurant;
		if (content != null && content.length() > 30) {
			this.content = content.substring(0, CONTENT_PREVIEW_LENGTH);
		} else {
			this.content = content;
		}
		this.rating = rating;
		this.createdAt = createdAt;
		this.tags = tags.stream().distinct().toList();
		this.images = images.stream().distinct().toList();
	}
}
