package com.fourdays.foodage.oauth.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/12/13 <br/>
 * description    : Kakao 로그인과 관련된 Exception  <br/>
 */
@Getter
public class OauthException extends RuntimeException {

	private ExceptionInfo errCode;

	public OauthException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
