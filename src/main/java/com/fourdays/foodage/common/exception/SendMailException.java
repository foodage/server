package com.fourdays.foodage.common.exception;

import lombok.Getter;

@Getter
public class SendMailException extends FoodageException {

	public SendMailException(ExceptionInfo errCode) {
		super(errCode);
	}
}
