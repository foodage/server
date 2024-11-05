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

	private String address;

	private String contents;

	private float rating;

	private LocalDateTime createdAt;

	private List<TagModel> tags;

	private List<ReviewMenuModel> menus;

	private List<ReviewImageModel> images;

	public ReviewModel(Long id, String restaurant, String address,
					   String contents, Float rating, LocalDateTime createdAt,
					   List<TagModel> tags, List<ReviewMenuModel> menus, List<ReviewImageModel> images) {

		this.id = id;
		this.restaurant = restaurant;
		this.address = address;
		if (contents != null && contents.length() > 30) {
			this.contents = contents.substring(0, CONTENT_PREVIEW_LENGTH);
		} else {
			this.contents = contents;
		}
		this.rating = rating;
		this.createdAt = createdAt;
		this.tags = tags.stream().distinct().toList();
		this.menus = menus.stream().distinct().toList();
		this.images = images.stream().distinct().toList();
	}
}
