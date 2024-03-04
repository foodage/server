package com.fourdays.foodage.oauth.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

@Getter
public class NotSupportedOauthServerTypeException extends RuntimeException {

	private ResultCode errCode;

	public NotSupportedOauthServerTypeException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
