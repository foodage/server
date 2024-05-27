package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/05/27 <br/>
 * description    : member id를 생성할 수 없을 경우 발생하는 에러 <br/>
 */
@Getter
public class MemberIdCreateFailedException extends RuntimeException {

	private ResultCode errCode;

	public MemberIdCreateFailedException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
