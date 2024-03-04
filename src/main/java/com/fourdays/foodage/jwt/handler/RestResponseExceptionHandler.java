package com.fourdays.foodage.jwt.handler;

import static org.springframework.http.HttpStatus.*;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fourdays.foodage.jwt.dto.ErrorDto;
import com.fourdays.foodage.jwt.exception.DuplicateMemberException;
import com.fourdays.foodage.jwt.exception.NotFoundMemberException;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

	@ResponseStatus(CONFLICT)
	@ExceptionHandler(value = {DuplicateMemberException.class})
	@ResponseBody
	protected ErrorDto conflict(RuntimeException ex, WebRequest request) {

		return new ErrorDto(CONFLICT.value(), ex.getMessage());
	}

	@ResponseStatus(FORBIDDEN)
	@ExceptionHandler(value = {NotFoundMemberException.class, AccessDeniedException.class})
	@ResponseBody
	protected ErrorDto forbidden(RuntimeException ex, WebRequest request) {

		return new ErrorDto(FORBIDDEN.value(), ex.getMessage());
	}
}
