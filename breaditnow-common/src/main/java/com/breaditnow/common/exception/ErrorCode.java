package com.breaditnow.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
	String defaultCode();
	HttpStatus defaultHttpStatus();
	String defaultMessage();
	RuntimeException defaultException();
	RuntimeException defaultException(Throwable cause);
}
