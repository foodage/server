package com.fourdays.foodage.home.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class RecentReviewResponse {

	private Long id;

	private String restaurant;

	private String address;

	private TagInfo tagInfo;

	private String thumbnailUrl;

	private LocalDateTime createdAt;

	public RecentReviewResponse(Long id, String restaurant, String address,
		String tagName, String tagColor, String thumbnailUrl,
		LocalDateTime createdAt) {

		this.id = id;
		this.restaurant = restaurant;
		this.address = address.substring(0, address.indexOf(
			' ', address.indexOf(' ') + 1)); // 전체 주소에서 시, 구 정보만 파싱 (ex. '서울특별시 영등포구')
		this.tagInfo = new TagInfo(tagName, tagColor);
		this.thumbnailUrl = thumbnailUrl;
		this.createdAt = createdAt;
	}

	@Getter
	public static class TagInfo {

		private String name;

		private String color;

		public TagInfo(String name, String color) {
			this.name = name;
			this.color = color;
		}
	}
}
