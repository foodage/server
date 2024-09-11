package com.fourdays.foodage.oauth.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class NotSupportedOauthServerTypeException extends FoodageException {

	public NotSupportedOauthServerTypeException(ExceptionInfo errCode) {
		super(errCode);
	}
}
