package com.fourdays.foodage.review.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReviewImageModel {

	@EqualsAndHashCode.Include
	@JsonIgnore
	private Long id;

	private Integer sequence; // 이미지 업로드 순서

	private String imageUrl;

	private Boolean useThumbnail;
}
