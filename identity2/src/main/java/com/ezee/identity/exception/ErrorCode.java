package com.ezee.identity.exception;

public enum ErrorCode {
	ID_NOT_FOUND_EXCEPTION("Id is not founded", 101), USER_NOT_FOUND_EXCEPTION("user is not found", 102),
	ALREADY_RECORED_EXISTS("Already record has been created", 103), DATA_MISMATCH("Enter a correct record", 104),
	DELETED_RECORD("record has been deleted", 105), INVALID_PERMISSION("Don't have the permision to allow", 106);

	private String message;
	private Integer code;

	private ErrorCode() {

	}

	private ErrorCode(String message, Integer code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

}
