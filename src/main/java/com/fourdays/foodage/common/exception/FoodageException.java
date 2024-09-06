package com.fourdays.foodage.common.exception;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/09/06 <br/>
 * description    : 반드시 예외처리가 필요한 Common Exception <br/>
 */
@Getter
public class FoodageException extends Exception {

	private ExceptionInfo errCode;

	public FoodageException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
