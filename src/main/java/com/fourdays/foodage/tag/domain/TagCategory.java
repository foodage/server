package com.fourdays.foodage.tag.domain;

public enum TagCategory {

	ALL("전체"),
	KOREAN("한식"),
	CHINESE("중식"),
	JAPANESE("일식"),
	WESTERN("양식"),
	SNACK_BAR("분식"),
	DESSERT("디저트"),
	COFFEE("커피"),
	BURGER("햄버거"),
	CHICKEN("치킨"),
	PIZZA("피자"),
	BUFFET("뷔페"),
	ASIAN("아시안"),
	PUB("술집"),
	MISC("잡식");

	private final String dbFieldValue;

	TagCategory(String dbFieldValue) {
		this.dbFieldValue = dbFieldValue;
	}

	public String getDbFieldValue() {
		return dbFieldValue;
	}
}
