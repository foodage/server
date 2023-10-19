package com.fourdays.foodage.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fourdays.foodage.common.dto.BaseResponse;
import com.fourdays.foodage.common.enums.BadRequestCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerConfig {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<BaseResponse<?>> handleException(Exception e) {

		log.error("handleException", e);
		BaseResponse<?> res = BaseResponse.error(BadRequestCode.ERR_INTERNAL, e.getMessage());

		return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<BaseResponse<?>> handleException(IllegalArgumentException e) {

		log.error("handleException", e);
		BaseResponse<?> res = BaseResponse.error(BadRequestCode.ERR_REQUIRED_FIELD, e.getMessage());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<BaseResponse<?>> handleException(UserException e) {

		log.error("handleException", e);
		BaseResponse<?> res = BaseResponse.error(BadRequestCode.ERR_REQUIRED_FIELD, e.getMessage());

		return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST); // or not_found 처리 고려
	}
}
