package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.domain.ValidationUtils;
import com.breaditnow.customer.common.exception.CustomerException;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_DND_DAYS;

public record DayOfWeekSet(Set<DayOfWeek> days) {
    public DayOfWeekSet(Set<DayOfWeek> days) {
        ValidationUtils.requireValid(days, Set::isEmpty, () -> new CustomerException(INVALID_DND_DAYS));
        this.days = EnumSet.copyOf(days);
    }

    public static DayOfWeekSet of(Set<DayOfWeek> days) {
        return new DayOfWeekSet(days);
    }

    public static DayOfWeekSet empty() {
        return new DayOfWeekSet(EnumSet.noneOf(DayOfWeek.class));
    }

    public boolean contains(DayOfWeek day) {
        return days.contains(day);
    }

    @Override
    public Set<DayOfWeek> days() {
        return Collections.unmodifiableSet(days);
    }
}
