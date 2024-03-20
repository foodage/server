package com.fourdays.foodage.common.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fourdays.foodage.common.enums.SearchBy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@JsonRootName(value = "searchValue")
@Getter
public class SearchKeywordFilter extends BaseSearchFilter {

	@Schema(title = "검색 종류")
	private SearchBy searchFilter;

	@Schema(title = "검색어")
	private String searchKeyword;

	public SearchBy getSearchFilter() {
		return searchFilter != null ? searchFilter : SearchBy.ALL;
	}

	public String getSearchKeyword() {
		return searchKeyword != null ? searchKeyword : "";
	}
}
