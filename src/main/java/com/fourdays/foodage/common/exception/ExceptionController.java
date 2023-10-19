package com.fourdays.foodage.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fourdays.foodage.common.dto.ErrorResponseDto;
import com.fourdays.foodage.common.enums.ResultCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(Exception e) {

		log.error("handleException", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(ResultCode.ERR_INTERNAL, e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(IllegalArgumentException e) {

		log.error("handleException", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(ResultCode.ERR_REQUIRED_FIELD, e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(UserException e) {

		log.error("handleException", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(ResultCode.ERR_REQUIRED_FIELD, e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}
}
