package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/12/28 <br/>
 * description    : Member의 상태와 관련된 Exception  <br/>
 */
@Getter
public class MemberStateException extends RuntimeException {

	private ResultCode errCode;
	private LoginResult loginResult;

	public MemberStateException(ResultCode errCode, LoginResult loginResult) {
		super(errCode.getMessage());
		this.errCode = errCode;
		this.loginResult = loginResult;
	}
}
