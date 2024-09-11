package com.fourdays.foodage.review.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fourdays.foodage.review.domain.Review;
import com.fourdays.foodage.review.domain.model.ReviewImageModel;
import com.fourdays.foodage.review.domain.model.ReviewMenuModel;
import com.fourdays.foodage.tag.domain.model.TagModel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateReviewRequestDto {

	private String restaurant;

	private String address;

	private float rating;

	private String contents;

	private LocalDateTime date;

	private List<Long> tagIds;

	private List<ReviewMenuModel> menus;

	private List<ReviewImageModel> images;

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	public static class ReviewMenuModel {

		private String menu;

		private Integer price;

		private Integer sequence;	// 메뉴 추가 순서
	}

	@NoArgsConstructor
	@AllArgsConstructor
	@Getter
	public static class ReviewImageModel {

		private String imageUrl;

		private Integer sequence; // 이미지 업로드 순서

		private Boolean isThumbnail;
	}
}