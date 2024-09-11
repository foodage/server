package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 회원가입 진행 과정에서 문제가 있을 시 발생하는 Exception  <br/>
 */
@Getter
public class MemberNotSupportedCharacterTypeException extends FoodageException {

	public MemberNotSupportedCharacterTypeException(ExceptionInfo errCode) {
		super(errCode);
	}
}
