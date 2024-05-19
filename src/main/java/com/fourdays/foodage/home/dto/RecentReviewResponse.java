package com.fourdays.foodage.home.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class RecentReviewResponse {

	Long id;
	String restaurant;
	String address;
	String tagName;
	LocalDateTime createdAt;

	public RecentReviewResponse(Long id, String restaurant, String address,
		Integer tagName, LocalDateTime createdAt) {

		this.id = id;
		this.restaurant = restaurant;
		this.address = address.substring(0, address.indexOf(' ',
			address.indexOf(' ') + 1)); // 전체 주소에서 시, 구 정보만 파싱 (ex. '서울특별시 영등포구')
		this.tagName = "tag entity 개발 후 수정";
		this.createdAt = createdAt;
	}
}
