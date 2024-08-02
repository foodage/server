package com.fourdays.foodage.tag.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TagModel {

	@EqualsAndHashCode.Include
	@JsonIgnore
	private Long id;

	private String name;

	private String bgColor;

	private String textColor;
}
