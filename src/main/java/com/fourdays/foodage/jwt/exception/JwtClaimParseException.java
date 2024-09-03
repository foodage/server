package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

public class JwtClaimParseException extends RuntimeException {

	public JwtClaimParseException(ExceptionInfo exceptionInfo) {
		super(exceptionInfo.getMessage());
	}
}
