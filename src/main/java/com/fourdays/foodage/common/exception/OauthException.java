package com.fourdays.foodage.common.exception;

import com.fourdays.foodage.common.enums.ResultCode;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/12/13 <br/>
 * description    : Kakao 로그인과 관련된 Exception  <br/>
 */
@Getter
public class OauthException extends RuntimeException {

	private ResultCode errCode;

	public OauthException(ResultCode errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
