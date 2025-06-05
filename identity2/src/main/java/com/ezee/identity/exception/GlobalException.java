package com.ezee.identity.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<ResponseException<Object>> handleServiceException(ServiceException ex) {
		ResponseException<Object> response = new ResponseException<>(ex.getCode(), ex.getMessage(),
				LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseException<Object>> serviceException(Exception e) {
		ResponseException<Object> response = new ResponseException<Object>(500,
				"Check the given record" + e.getMessage(), LocalDateTime.now());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
