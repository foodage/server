package com.fourdays.foodage.review.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fourdays.foodage.common.enums.ResultCode;
import com.fourdays.foodage.review.exception.ThumbnailNotFoundException;
import com.fourdays.foodage.tag.domain.model.TagModel;

import lombok.Getter;

@Getter
public class ReviewModelWithThumbnail {

	@JsonIgnore
	private static final int CONTENT_PREVIEW_LENGTH = 30;

	private Long id;

	private String restaurant;

	private String content;

	private float rating;

	private LocalDateTime createdAt;

	private List<TagModel> tags;

	private ReviewThumbnail thumbnail;

	public ReviewModelWithThumbnail(Long id, String restaurant, String content, Float rating,
		LocalDateTime createdAt, List<TagModel> tags, List<ReviewImageModel> thumbnail) {

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
		this.thumbnail = new ReviewThumbnail(thumbnail.stream().distinct().toList());
	}

	@Getter
	public static class ReviewThumbnail {

		private int totalCount;

		private String imageUrl;

		public ReviewThumbnail(List<ReviewImageModel> thumbnail) {
			this.totalCount = !CollectionUtils.isEmpty(thumbnail)
				? thumbnail.size()
				: 0;
			this.imageUrl = !CollectionUtils.isEmpty(thumbnail)
				? thumbnail.stream()
				.filter(ReviewImageModel::getUseThumbnail)
				.findFirst()
				.orElseThrow(() -> new ThumbnailNotFoundException(ResultCode.ERR_THUMBNAIL_NOT_FOUND))
				.getImageUrl()
				: null;
		}
	}
}
