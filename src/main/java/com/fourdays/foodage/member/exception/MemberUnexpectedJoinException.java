package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 비정상적인 방법으로 회원가입을 시도했을경우 <br/>
 */
@Getter
public class MemberUnexpectedJoinException extends RuntimeException {

	private ResultCode errCode;

	public MemberUnexpectedJoinException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
