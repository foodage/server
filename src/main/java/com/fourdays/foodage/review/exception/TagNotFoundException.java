package com.fourdays.foodage.review.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

public class TagNotFoundException extends FoodageException {

	public TagNotFoundException(ExceptionInfo errCode) {
		super(errCode);
	}
}
