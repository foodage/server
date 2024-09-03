package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 회원가입 진행중인 상태에 관련된 Exception  <br/>
 */
@Getter
public class MemberJoinInProgressException extends RuntimeException {

	private ExceptionInfo errCode;
	private LoginResult loginResult;

	public MemberJoinInProgressException(ExceptionInfo errCode, LoginResult loginResult) {
		super(errCode.getMessage());
		this.errCode = errCode;
		this.loginResult = loginResult;
	}
}
