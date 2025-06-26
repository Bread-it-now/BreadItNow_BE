package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.breaditnow.common.util.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.EMPTY_DND_DAYS;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_DND_DAY_VALUE;

public record DayOfWeekSet(Set<DayOfWeek> days) {
    public static DayOfWeekSet of(Set<String> days) {
        requireValid(days, d -> d == null || d.isEmpty(), () -> new CustomerException(EMPTY_DND_DAYS));

        try{
            return new DayOfWeekSet(days.stream()
                            .map(String::toUpperCase)
                            .map(DayOfWeek::valueOf)
                            .collect(Collectors.toSet()));
        } catch (IllegalArgumentException e) {
            throw new CustomerException(INVALID_DND_DAY_VALUE);
        }

    }

    public static DayOfWeekSet empty() {
        return new DayOfWeekSet(Collections.emptySet());
    }

    public boolean contains(DayOfWeek day) {
        return days.contains(day);
    }

    @Override
    public Set<DayOfWeek> days() {
        return Collections.unmodifiableSet(days);
    }
}