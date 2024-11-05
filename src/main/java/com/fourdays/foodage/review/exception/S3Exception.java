package com.fourdays.foodage.review.exception;

import com.fourdays.foodage.common.enums.ResultCode;

public class S3Exception extends RuntimeException {

    private final ResultCode errCode;

    public S3Exception(ResultCode errCode) {
        super(errCode.getMessage());
        this.errCode = errCode;
    }
}
