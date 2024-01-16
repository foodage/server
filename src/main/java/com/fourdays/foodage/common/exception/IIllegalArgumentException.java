package com.fourdays.foodage.common.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/09 <br/>
 * description    : 처리 불가능한 파라미터  <br/>
 */
@Getter
public class IIllegalArgumentException extends Exception {

	private ResultCode errCode;

	public IIllegalArgumentException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
