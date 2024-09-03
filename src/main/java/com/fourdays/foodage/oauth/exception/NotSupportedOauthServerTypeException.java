package com.fourdays.foodage.oauth.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

@Getter
public class NotSupportedOauthServerTypeException extends RuntimeException {

	private ExceptionInfo errCode;

	public NotSupportedOauthServerTypeException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
