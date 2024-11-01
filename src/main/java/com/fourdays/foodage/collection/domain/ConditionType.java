package com.fourdays.foodage.collection.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ConditionType {

	REVIEW_COUNT("작성 리뷰 개수"),
	TAG_USAGE("전체 태그 사용 횟수"),
	KOREAN_TAG_USAGE("한식 태그 사용 횟수"),
	CHINESE_TAG_USAGE("중식 태그 사용 횟수"),
	JAPANESE_TAG_USAGE("일식 태그 사용 횟수"),
	WESTERN_TAG_USAGE("양식 태그 사용 횟수"),
	SNACK_BAR_TAG_USAGE("분식 태그 사용 횟수"),
	DESSERT_TAG_USAGE("디저트 태그 사용 횟수"),
	COFFEE_TAG_USAGE("커피 태그 사용 횟수"),
	BURGER_TAG_USAGE("햄버거 태그 사용 횟수"),
	CHICKEN_TAG_USAGE("치킨 태그 사용 횟수"),
	PIZZA_TAG_USAGE("피자 태그 사용 횟수"),
	BUFFET_TAG_USAGE("뷔페 태그 사용 횟수"),
	ASIAN_TAG_USAGE("아시안 태그 사용 횟수"),
	PUB_TAG_USAGE("술집 태그 사용 횟수"),
	MISC_TAG_USAGE("잡식 태그 사용 횟수");

	private final String displayName;
}
