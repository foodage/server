package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.enums.ResultCode;

public class BlockedRefreshTokenException extends RuntimeException {

	public BlockedRefreshTokenException(ResultCode resultCode) {
		super(resultCode.getMessage());
	}
}
