package com.fourdays.foodage.common.exception;

import lombok.Getter;

@Getter
public abstract class FoodageException extends RuntimeException {

	private ExceptionInfo errCode;

	public FoodageException(ExceptionInfo errCode) {
		super(errCode.message());
		this.errCode = errCode;
	}
}
