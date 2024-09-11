package com.fourdays.foodage.member.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2023/10/19 <br/>
 * description    : Member와 관련된 Exception  <br/>
 */
@Getter
public class MemberNotJoinedException extends FoodageException {

	public MemberNotJoinedException(ExceptionInfo errCode) {
		super(errCode);
	}
}
