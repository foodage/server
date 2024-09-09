package com.fourdays.foodage.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fourdays.foodage.common.dto.ErrorResponseDto;
import com.fourdays.foodage.member.exception.MemberInvalidOauthServerTypeException;
import com.fourdays.foodage.member.exception.MemberInvalidStateException;
import com.fourdays.foodage.member.exception.MemberJoinInProgressException;
import com.fourdays.foodage.member.exception.MemberJoinUnexpectedException;
import com.fourdays.foodage.member.exception.MemberJoinedException;
import com.fourdays.foodage.member.exception.MemberLeaveException;
import com.fourdays.foodage.member.exception.MemberMismatchAccountEmailException;
import com.fourdays.foodage.member.exception.MemberNotFoundException;
import com.fourdays.foodage.member.exception.MemberNotJoinedException;
import com.fourdays.foodage.member.exception.MemberNotSupportedCharacterTypeException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class MemberExceptionHandler {

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberNotFoundException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	////////////////////////////////////////////////
	// join
	////////////////////////////////////////////////
	@ExceptionHandler(MemberNotJoinedException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberNotJoinedException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	@ExceptionHandler(MemberJoinedException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberJoinedException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	@ExceptionHandler(MemberJoinUnexpectedException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberJoinUnexpectedException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	@ExceptionHandler(MemberJoinInProgressException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberJoinInProgressException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	////////////////////////////////////////////////
	// invalid param
	////////////////////////////////////////////////
	@ExceptionHandler(MemberMismatchAccountEmailException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberMismatchAccountEmailException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	@ExceptionHandler(MemberNotSupportedCharacterTypeException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberNotSupportedCharacterTypeException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	@ExceptionHandler(MemberInvalidStateException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberInvalidStateException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	@ExceptionHandler(MemberInvalidOauthServerTypeException.class)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberInvalidOauthServerTypeException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus()); // or not_found 처리 고려
	}

	////////////////////////////////////////////////
	// leave
	////////////////////////////////////////////////
	@ExceptionHandler(MemberLeaveException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorResponseDto<?>> handleException(MemberLeaveException e) {

		log.error("handleException : {}", e);
		ErrorResponseDto<?> res = ErrorResponseDto.error(e.getErrCode(), e.getMessage());

		return new ResponseEntity<>(res, res.getHttpStatus());
	}
}
