package com.breaditnow.owner.common.presentation.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.breaditnow.external.global.exception.ExternalErrorCode;

@Target(METHOD)
@Retention(RUNTIME)
public @interface ExternalErrorCodeExamples {
	ExternalErrorCode[] value();
}
