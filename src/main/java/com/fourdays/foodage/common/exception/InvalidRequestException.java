package com.fourdays.foodage.common.exception;

import lombok.Getter;

@Getter
public class InvalidRequestException extends FoodageException {

	public InvalidRequestException(ExceptionInfo errCode) {
		super(errCode);
	}
}
