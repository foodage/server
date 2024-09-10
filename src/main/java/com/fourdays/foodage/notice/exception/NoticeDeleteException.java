package com.fourdays.foodage.notice.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;

import lombok.Getter;

@Getter
public class NoticeDeleteException extends RuntimeException {

	private ExceptionInfo errCode;

	public NoticeDeleteException(ExceptionInfo errCode) {
		super(errCode.getMessage());
		this.errCode = errCode;
	}
}
