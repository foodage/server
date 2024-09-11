package com.fourdays.foodage.review.exception;

import com.fourdays.foodage.common.exception.ExceptionInfo;
import com.fourdays.foodage.common.exception.FoodageException;

import lombok.Getter;

/**
 * author         : ebkim <br/>
 * date           : 2024/08/02 <br/>
 * description    : 리뷰 이미지는 존재하는데 썸네일이 없는 경우 <br/>
 */
@Getter
public class ThumbnailNotFoundException extends FoodageException {
	
	public ThumbnailNotFoundException(ExceptionInfo errCode) {
		super(errCode);
	}
}
