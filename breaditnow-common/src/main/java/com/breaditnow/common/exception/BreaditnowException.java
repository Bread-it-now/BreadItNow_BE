package com.breaditnow.common.exception;

import lombok.Getter;

@Getter
public class BreaditnowException extends RuntimeException {

	private final ErrorCode errorCode;

	public BreaditnowException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public BreaditnowException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.errorCode = errorCode;
	}

	public BreaditnowException(ErrorCode errorCode, String message) {
		super(errorCode.getMessage() + " : " + message);
		this.errorCode = errorCode;
	}
}
