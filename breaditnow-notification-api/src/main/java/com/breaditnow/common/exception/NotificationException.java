package com.breaditnow.common.exception;

public class NotificationException extends BreaditnowException {

	public NotificationException(NotificationErrorCode errorCode) {
		super(errorCode);
	}

	public NotificationException(NotificationErrorCode errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}

