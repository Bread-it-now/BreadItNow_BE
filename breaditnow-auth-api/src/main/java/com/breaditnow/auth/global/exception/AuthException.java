package com.breaditnow.auth.global.exception;

import com.breaditnow.common.exception.BreaditnowException;

public class AuthException extends BreaditnowException {

	public AuthException(AuthenticationErrorCode errorCode) {
		super(errorCode);
	}

	public AuthException(AuthenticationErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

