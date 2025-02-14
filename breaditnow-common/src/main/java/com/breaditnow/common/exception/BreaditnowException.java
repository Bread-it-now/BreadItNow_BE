package com.breaditnow.common.exception;

public abstract class BreaditnowException extends RuntimeException{

	private final ErrorCode errorCode;

	protected BreaditnowException(ErrorCode errorCode) {
		super(errorCode.defaultMessage());
		this.errorCode = errorCode;
	}

	public BreaditnowException(ErrorCode errorCode, Throwable cause) {
		super(errorCode.defaultMessage(), cause);
		this.errorCode = errorCode;
	}
}
