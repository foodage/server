package com.fourdays.foodage.inquiry.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class InquiryAnswerContentsException extends FoodageException {

	public InquiryAnswerContentsException(ExceptionInfo errCode) {
		super(errCode);
	}
}
