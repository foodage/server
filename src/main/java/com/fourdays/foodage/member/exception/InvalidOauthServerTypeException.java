package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

@Getter
public class InvalidOauthServerTypeException extends RuntimeException {

	private ResultCode errCode;

	public InvalidOauthServerTypeException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
