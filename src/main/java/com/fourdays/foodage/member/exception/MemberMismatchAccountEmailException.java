package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 로그인 한 계정의 email과 가입 이메일이 다를 경우 <br/>
 */
@Getter
public class MemberMismatchAccountEmailException extends RuntimeException {

	private ExceptionInfo errCode;

	public MemberMismatchAccountEmailException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
