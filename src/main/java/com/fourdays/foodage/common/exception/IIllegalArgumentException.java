package com.fourdays.foodage.common.exception;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/09 <br/>
 * description    : 처리 불가능한 파라미터  <br/>
 */
@Getter
public class IIllegalArgumentException extends Exception {

	private ExceptionInfo errCode;

	public IIllegalArgumentException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
