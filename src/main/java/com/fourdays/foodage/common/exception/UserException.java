package com.fourdays.foodage.common.exception;

import com.fourdays.foodage.common.enums.BadRequestCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : User와 관련된 Exception  <br/>
 */
@Getter
public class UserException extends RuntimeException {

	private BadRequestCode errCode;

	public UserException(BadRequestCode errCode) {
		super(errCode.getResultMsg());
		this.errCode = errCode;
	}

	public UserException(BadRequestCode errCode, String errMessage) {
		super(errCode.customResultMsg(errMessage));
		this.errCode = errCode;
	}
}
