package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 존재하지 않는 사용자 정보일 경우 발생하는 Exception  <br/>
 */
@Getter
public class MemberNotFoundException extends RuntimeException {

	private ResultCode errCode;

	public MemberNotFoundException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
