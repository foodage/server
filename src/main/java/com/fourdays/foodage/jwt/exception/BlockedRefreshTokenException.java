package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

@Getter
public class BlockedRefreshTokenException extends RuntimeException {

	private ResultCode errCode;

	public BlockedRefreshTokenException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
