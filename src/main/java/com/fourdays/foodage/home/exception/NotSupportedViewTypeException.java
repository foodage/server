package com.fourdays.foodage.home.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/06/17 <br/>
 * description    : 지원하지 않는 viewType으로 리뷰를 조회할 경우 발생하는 Exception  <br/>
 */
@Getter
public class NotSupportedViewTypeException extends FoodageException {

	public NotSupportedViewTypeException(ExceptionInfo errCode) {
		super(errCode);
	}
}
