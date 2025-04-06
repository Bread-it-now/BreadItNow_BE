package com.breaditnow.owner.global.swagger.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.breaditnow.owner.global.exception.OwnerErrorCode;

@Target(METHOD)
@Retention(RUNTIME)
public @interface OwnerApiErrorCodeExamples {
	OwnerErrorCode[] value();
}
