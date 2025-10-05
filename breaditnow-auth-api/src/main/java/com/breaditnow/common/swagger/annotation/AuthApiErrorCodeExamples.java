package com.breaditnow.common.swagger.annotation;

import com.breaditnow.common.exception.AuthErrorCode;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface AuthApiErrorCodeExamples {
	AuthErrorCode[] value();
}
