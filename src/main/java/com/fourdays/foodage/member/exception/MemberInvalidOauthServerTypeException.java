package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

@Getter
public class MemberInvalidOauthServerTypeException extends RuntimeException {

	private ExceptionInfo errCode;

	public MemberInvalidOauthServerTypeException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
