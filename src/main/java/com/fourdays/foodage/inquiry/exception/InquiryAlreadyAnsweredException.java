package com.fourdays.foodage.inquiry.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class InquiryAlreadyAnsweredException extends FoodageException {

	public InquiryAlreadyAnsweredException(ExceptionInfo errCode) {
		super(errCode);
	}
}
