package com.fourdays.foodage.common.exception;

import com.fourdays.foodage.common.enums.BadRequestCode;

/**
 * author         : ebkim <br/>
 * date           : 2023. 09 <br/>
 * description    : 처리 불가능한 파라미터  <br/>
 */
public class IIllegalArgumentException extends Exception {

	private BadRequestCode errCode;

	public IIllegalArgumentException(BadRequestCode errCode) {
		super(errCode.getResultMsg());
		this.errCode = errCode;
	}

	public IIllegalArgumentException(BadRequestCode errCode, String errMessage) {
		super(errCode.customResultMsg(errMessage));
		this.errCode = errCode;
	}

	public BadRequestCode getErrorCode() {
		return errCode;
	}

	public String getErrorMsg() {
		return errCode.getResultMsg();
	}
}
