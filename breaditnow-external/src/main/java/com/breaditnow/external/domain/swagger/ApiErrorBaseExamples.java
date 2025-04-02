package com.breaditnow.external.domain.swagger;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RUNTIME)
public @interface ApiErrorBaseExamples {
	ApiErrorBaseExample[] value();
}
