package com.fourdays.foodage.common.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : User와 관련된 Exception  <br/>
 */
@Getter
public class UserException extends RuntimeException {

	private ResultCode errCode;

	public UserException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
