package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/05/27 <br/>
 * description    : 유효하지 않은 인자값을 입력했을 경우 발생하는 에러 <br/>
 */
@Getter
public class InvalidArgsException extends RuntimeException {

	private ExceptionInfo errCode;

	public InvalidArgsException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
