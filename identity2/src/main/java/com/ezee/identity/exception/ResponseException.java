package com.ezee.identity.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ResponseException<T> {
	private T data;
	private String message;
	private Integer errorCode;
	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	private int status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime responseTime;

	public ResponseException() {

	}

	public ResponseException(Integer errorCode, String message, LocalDateTime responseTime) {
		this.errorCode = errorCode;
		this.message = message;
		this.responseTime = responseTime;
	}

	public ResponseException(int status, T data, LocalDateTime responseTime) {
		this.status = status;
		this.data = data;
		this.responseTime = responseTime;

	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(LocalDateTime responseTime) {
		this.responseTime = responseTime;
	}

	public static <T> ResponseEntity<?> success(T data) {
		ResponseException<T> response = new ResponseException<>();
		response.setData(data);
		response.setResponseTime(LocalDateTime.now());
		response.setStatus(1);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	public static <T> ResponseEntity<?> failure(ErrorCode errorCode) {
		ResponseException<T> response = new ResponseException<T>();
		response.setErrorCode(errorCode.getCode());
		response.setMessage(errorCode.getMessage());
		response.setResponseTime(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}
}
