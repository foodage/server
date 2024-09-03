package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : Member와 관련된 Exception  <br/>
 */
@Getter
public class MemberNotJoinedException extends RuntimeException {

	private ExceptionInfo errCode;

	public MemberNotJoinedException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
