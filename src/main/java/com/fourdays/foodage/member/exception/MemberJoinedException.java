package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/02/23 <br/>
 * description    : 이미 가입한 Member일 경우 발생하는 Exception <br/>
 */
@Getter
public class MemberJoinedException extends FoodageException {

	public MemberJoinedException(ExceptionInfo errCode) {
		super(errCode);
	}
}
