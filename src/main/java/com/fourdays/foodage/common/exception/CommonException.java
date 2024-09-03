package com.fourdays.foodage.common.exception;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : 기본 Exception  <br/>
 */
@Getter
public class CommonException extends Exception {

	private ExceptionInfo errCode;

	public CommonException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
