package com.fourdays.foodage.common.domain;

import java.util.List;

import lombok.Getter;

@Getter
public abstract class BaseSearchPageRequestDto extends BasePageRequest {

	private List<BaseSearchFilter> searchFilter;
}
