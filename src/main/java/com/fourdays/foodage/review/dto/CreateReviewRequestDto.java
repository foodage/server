package com.fourdays.foodage.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

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

	@Nullable
	private List<ReviewMenuModel> menus;

	@Nullable
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