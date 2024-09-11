package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

public class JwtClaimParseException extends FoodageException {

	public JwtClaimParseException(ExceptionInfo errCode) {
		super(errCode);
	}
}
