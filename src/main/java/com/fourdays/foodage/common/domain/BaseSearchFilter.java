package com.fourdays.foodage.common.domain;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fourdays.foodage.common.enums.SearchType;

import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonTypeInfo(
	use = JsonTypeInfo.Id.NAME,
	include = JsonTypeInfo.As.EXISTING_PROPERTY,
	property = "searchType",
	visible = true
)
@JsonSubTypes({
	@JsonSubTypes.Type(value = SearchDateFilter.class, name = "date"),
	@JsonSubTypes.Type(value = SearchKeywordFilter.class, name = "keyword"),
	// @JsonSubTypes.Type(value = GetVcSchemaListRequestDto.SearchExtraFilter.class), // 이너클래스 내에서 @JsonTypeName으로 처리

})
@NoArgsConstructor
@Getter
public abstract class BaseSearchFilter {

	private SearchType searchType;
	// searchValue는 각 subType 클래스 내에 구현
}
