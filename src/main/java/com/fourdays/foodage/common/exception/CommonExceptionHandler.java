package com.fourdays.foodage.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fourdays.foodage.common.dto.ErrorResponseDto;
import com.fourdays.foodage.jwt.exception.BlockedRefreshTokenException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(Exception e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(ExceptionInfo.ERR_INTERNAL, e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(IllegalArgumentException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(ExceptionInfo.ERR_REQUIRED_FIELD, e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public Object handleException(DataIntegrityViolationException e) {

		log.debug("handleException : {}", e.getMessage());
		ErrorResponseDto<?> res = ErrorResponseDto.error(ExceptionInfo.ERR_REQUIRED_FIELD, e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleException(MethodArgumentNotValidException e) {
		String message = e.getBindingResult()
			.getFieldError()
			.getDefaultMessage();

		log.debug("handleException : {}", message);
		ErrorResponseDto<?> res = ErrorResponseDto.error(ExceptionInfo.ERR_REQUIRED_FIELD, message);

		return new ResponseEntity<>(res, res.getHttpStatus());
	}

	@ExceptionHandler(BlockedRefreshTokenException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(BlockedRefreshTokenException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}
}
