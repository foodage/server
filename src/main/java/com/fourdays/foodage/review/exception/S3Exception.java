package com.fourdays.foodage.review.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

public class S3Exception extends FoodageException {

	public S3Exception(ExceptionInfo errCode) {
		super(errCode);
	}
}
