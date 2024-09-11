package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class MemberInvalidOauthServerTypeException extends FoodageException {

	public MemberInvalidOauthServerTypeException(ExceptionInfo errCode) {
		super(errCode);
	}
}
