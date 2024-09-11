package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 존재하지 않는 사용자 정보일 경우 발생하는 Exception  <br/>
 */
@Getter
public class MemberNotFoundException extends FoodageException {

	public MemberNotFoundException(ExceptionInfo errCode) {
		super(errCode);
	}
}
