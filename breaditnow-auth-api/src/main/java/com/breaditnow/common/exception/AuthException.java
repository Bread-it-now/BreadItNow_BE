package com.breaditnow.common.exception;

public class AuthException extends BreaditnowException {

	public AuthException(AuthErrorCode errorCode) {
		super(errorCode);
	}

	public AuthException(AuthErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public AuthException(AuthErrorCode errorCode, String message) {
		super(errorCode, message);
	}

	public AuthException(CommonErrorCode commonErrorCode) {
		super(commonErrorCode);
	}
}
