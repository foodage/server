package com.fourdays.foodage.common.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : 기본 Exception  <br/>
 */
@Getter
public class CommonException extends Exception {

	private ResultCode errCode;

	public CommonException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
