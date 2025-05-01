package com.breaditnow.customer.global.swagger.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.breaditnow.customer.global.exception.CustomerErrorCode;

@Target(METHOD)
@Retention(RUNTIME)
public @interface CustomerApiErrorCodeExamples {
	CustomerErrorCode[] value();
}
