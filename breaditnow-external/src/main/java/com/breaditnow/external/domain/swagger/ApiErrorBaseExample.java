package com.breaditnow.external.domain.swagger;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.breaditnow.common.exception.ErrorCode;

@Target(METHOD)
@Retention(RUNTIME)
public @interface ApiErrorBaseExample {
	/**
	 * ErrorCode 인터페이스를 구현한 enum 클래스.
	 * 예) AuthErrorCode.class, DomainErrorCode.class, CustomerErrorCode.class
	 */
	Class<? extends Enum<? extends ErrorCode>> codeClass();

	/**
	 * 해당 enum 클래스 내부의 상수 이름
	 * 예) "EXPIRED_TOKEN", "NOT_FOUND_CUSTOMER"
	 */
	String codeConstant();
}
