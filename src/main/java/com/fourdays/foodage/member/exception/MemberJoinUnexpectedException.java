package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 비정상적인 방법으로 회원가입을 시도했을경우 <br/>
 */
@Getter
public class MemberJoinUnexpectedException extends RuntimeException {

	private ExceptionInfo errCode;

	public MemberJoinUnexpectedException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
