package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : Member와 관련된 Exception  <br/>
 */
@Getter
public class MemberException extends RuntimeException {

	private ResultCode errCode;

	public MemberException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
