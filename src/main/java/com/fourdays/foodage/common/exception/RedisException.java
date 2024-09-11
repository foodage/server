package com.fourdays.foodage.common.exception;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/09/05 <br/>
 */
@Getter
public class RedisException extends FoodageException {

	public RedisException(ExceptionInfo errCode) {
		super(errCode);
	}
}
