package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 회원가입 진행 과정에서 문제가 있을 시 발생하는 Exception  <br/>
 */
@Getter
public class CharacterTypeNotSupportedException extends RuntimeException {

	private ResultCode errCode;

	public CharacterTypeNotSupportedException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
