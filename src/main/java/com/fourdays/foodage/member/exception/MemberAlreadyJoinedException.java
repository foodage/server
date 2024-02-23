package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 이미 존재하는 member <br/>
 */
@Getter
public class MemberExistException extends RuntimeException {

	private ResultCode errCode;

	public MemberExistException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
