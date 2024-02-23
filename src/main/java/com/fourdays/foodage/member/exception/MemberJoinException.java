package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 회원가입과 관련된 Exception  <br/>
 */
@Getter
public class MemberJoinException extends RuntimeException {

	private ResultCode errCode;

	public MemberJoinException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
