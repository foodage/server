package com.fourdays.foodage.review.exception;

import com.fourdays.foodage.common.enums.ResultCode;

public class TagNotFoundException extends RuntimeException {

    private ResultCode errCode;

    public TagNotFoundException(ResultCode errCode) {
        super(errCode.getMessage());
        this.errCode = errCode;
    }
}
