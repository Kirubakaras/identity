package com.ezee.identity.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private ErrorCode errorCode;

	public ServiceException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return errorCode.getMessage();
	}

	public Integer getCode() {
		return errorCode.getCode();
	}

	public ErrorCode getErrorcode() {
		return errorCode;
	}

	public void setErrorcode(ErrorCode errorcode) {
		this.errorCode = errorcode;
	}
}
