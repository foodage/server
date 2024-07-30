package com.fourdays.foodage.tag.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TagInfo {

	@EqualsAndHashCode.Include
	@JsonIgnore
	private Long id;

	private String name;

	private String bgColor;

	private String textColor;
}
