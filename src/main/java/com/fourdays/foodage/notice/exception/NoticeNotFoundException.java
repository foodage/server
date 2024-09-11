package com.fourdays.foodage.notice.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class NoticeNotFoundException extends FoodageException {

	public NoticeNotFoundException(ExceptionInfo errCode) {
		super(errCode);
	}
}
