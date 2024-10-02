package com.fourdays.foodage.notice.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

@Getter
public class NoticeDeleteException extends FoodageException {

	public NoticeDeleteException(ExceptionInfo errCode) {
		super(errCode);
	}
}
