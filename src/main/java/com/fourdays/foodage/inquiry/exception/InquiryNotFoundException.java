package com.fourdays.foodage.inquiry.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class InquiryNotFoundException extends FoodageException {

	public InquiryNotFoundException(ExceptionInfo errCode) {
		super(errCode);
	}
}
