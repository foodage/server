package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 이미 가입한 Member일 경우 발생하는 Exception <br/>
 */
@Getter
public class MemberDuplicateNicknameException extends RuntimeException {

	private ExceptionInfo errCode;

	public MemberDuplicateNicknameException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
