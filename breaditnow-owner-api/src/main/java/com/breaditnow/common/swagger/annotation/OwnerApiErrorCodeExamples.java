package com.breaditnow.common.swagger.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.breaditnow.common.exception.OwnerErrorCode;

@Target(METHOD)
@Retention(RUNTIME)
public @interface OwnerApiErrorCodeExamples {
	OwnerErrorCode[] value();
}
