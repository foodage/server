package com.fourdays.foodage.review.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewImageModel {

	private Integer sequence; // 이미지 업로드 순서

	private String imageUrl;

	private Boolean useThumbnail;
}
