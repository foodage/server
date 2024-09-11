package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/09/05 <br/>
 */
@Getter
public class MemberLeaveException extends FoodageException {

	public MemberLeaveException(ExceptionInfo errCode) {
		super(errCode);
	}
}
