package com.breaditnow.customer.domain.common.core;

import com.breaditnow.common.exception.BreaditnowException;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ValidationUtils {
    private ValidationUtils() {}

    public static <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) setter.accept(value);
    }

    public static <T> void setIfValid(T value, Predicate<T> validator, Consumer<T> setter, Supplier<? extends BreaditnowException> exceptionSupplier) {
        if (value == null) return;
        if (validator.test(value)) throw exceptionSupplier.get();
        setter.accept(value);
    }

    public static <T> void requireValid(T value, Predicate<T> validator, Supplier<? extends BreaditnowException> exceptionSupplier) {
        if (value == null || validator.test(value)) {
            throw exceptionSupplier.get();
        }
    }
}
