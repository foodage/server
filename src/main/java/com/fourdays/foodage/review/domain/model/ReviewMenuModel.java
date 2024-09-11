package com.fourdays.foodage.review.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ReviewMenuModel {

	@EqualsAndHashCode.Include
	@JsonIgnore
	private Long id;

	private String menu;

	private Integer price;

	private Long reviewId;
}
