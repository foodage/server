package com.fourdays.foodage.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fourdays.foodage.common.dto.ErrorResponseDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class UncaughtExceptionHandler {

	@ExceptionHandler(FoodageException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(final FoodageException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(),
			e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public Object handleException(final DataIntegrityViolationException e) {

		log.error("handleException : {}", e.getMessage());
		ErrorResponseDto<?> res = ErrorResponseDto.error(ExceptionInfo.ERR_REQUIRED_FIELD, e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleException(final MethodArgumentNotValidException e) {

		String message = e.getBindingResult()
			.getFieldError()
			.getDefaultMessage();

		log.error("handleException : {}", message);
		ErrorResponseDto<?> res = ErrorResponseDto.error(ExceptionInfo.ERR_REQUIRED_FIELD, message);

		return new ResponseEntity<>(res, res.getHttpStatus());
	}
}
