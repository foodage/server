package com.fourdays.foodage.jwt.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class BlockedRefreshTokenException extends FoodageException {

	public BlockedRefreshTokenException(ExceptionInfo errCode) {
		super(errCode);
	}
}
