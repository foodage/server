package com.fourdays.foodage.common.exception;

import com.fourdays.foodage.common.enums.BadRequestCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : 기본 Exception  <br/>
 */
@Getter
public class CommonException extends Exception {

	private BadRequestCode errCode;

	public CommonException(BadRequestCode errCode) {
		super(errCode.getResultMsg());
		this.errCode = errCode;
	}

	public CommonException(BadRequestCode errCode, String errMessage) {
		super(errCode.customResultMsg(errMessage));
		this.errCode = errCode;
	}
}
