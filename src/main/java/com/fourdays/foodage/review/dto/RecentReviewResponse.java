package com.fourdays.foodage.review.dto;

import java.time.LocalDateTime;

import com.fourdays.foodage.tag.dto.TagInfo;

import lombok.Getter;

@Getter
public class RecentReviewResponse {

	private Long id;

	private String restaurant;

	private String address;

	private TagInfo tag;

	private String thumbnailUrl;

	private LocalDateTime createdAt;

	public RecentReviewResponse(Long id, String restaurant, String address,
		String tagName, String tagBgColor, String tagTextColor,
		String thumbnailUrl, LocalDateTime createdAt) {

		this.id = id;
		this.restaurant = restaurant;
		this.address = address.substring(0, address.indexOf(
			' ', address.indexOf(' ') + 1)); // 전체 주소에서 시, 구 정보만 파싱 (ex. '서울특별시 영등포구')
		this.tag = new TagInfo(tagName, tagBgColor, tagTextColor);
		this.thumbnailUrl = thumbnailUrl;
		this.createdAt = createdAt;
	}
}
