package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

@Getter
public class BlockedRefreshTokenException extends RuntimeException {

	private ExceptionInfo errCode;

	public BlockedRefreshTokenException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
