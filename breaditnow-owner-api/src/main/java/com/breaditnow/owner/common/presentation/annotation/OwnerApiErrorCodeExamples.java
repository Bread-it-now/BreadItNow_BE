package com.breaditnow.owner.common.presentation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.breaditnow.owner.common.exception.OwnerErrorCode;

@Target(METHOD)
@Retention(RUNTIME)
public @interface OwnerApiErrorCodeExamples {
	OwnerErrorCode[] value();
}
