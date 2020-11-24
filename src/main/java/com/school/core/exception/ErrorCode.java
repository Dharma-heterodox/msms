package com.school.core.exception;

/**
 * <p>
 * Handle child statistics error code.
 * </p>
 * 
 * @author Rajasankar
 *
 */
public enum ErrorCode implements ErrorHandle {
	SCHOOL_MANAGEMENT_2001(2001, "Something went wrong. Please contact the administrator."),
	SCHOOL_MANAGEMENT_2002(2002, "No such child status present"), 
	SCHOOL_MANAGEMENT_2003(2003, "App Id Required");

	private final int code;
	private final String message;

	ErrorCode(int errorCode, String message) {
		this.code = errorCode;
		this.message = message;
	}

	@Override
	public int getErrorCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
