package com.breaditnow.auth.global.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class AuthException extends BreaditnowException {

	public AuthException(AuthErrorCode errorCode) {
		super(errorCode);
	}

	public AuthException(AuthErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public AuthException(AuthErrorCode errorCode, String message) {
		super(errorCode,  message);
	}
}

