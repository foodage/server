package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.enums.ResultCode;

public class JwtClaimParseException extends RuntimeException {

	public JwtClaimParseException(ResultCode resultCode) {
		super(resultCode.getMessage());
	}
}
