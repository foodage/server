package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.LoginResult;
import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/12/28 <br/>
 * description    : Member의 상태와 관련된 Exception  <br/>
 */
@Getter
public class MemberInvalidStateException extends FoodageException {

	private LoginResult loginResult;

	public MemberInvalidStateException(ExceptionInfo errCode, LoginResult loginResult) {
		super(errCode);
		this.loginResult = loginResult;
	}
}
