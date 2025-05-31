package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.domain.ValidationUtils;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_DND_DAYS;

@Getter
@EqualsAndHashCode
public class DayOfWeekSet {
    private final Set<DayOfWeek> days;

    public DayOfWeekSet(Set<DayOfWeek> days) {
        ValidationUtils.requireValid(days, Set::isEmpty, () -> new CustomerException(INVALID_DND_DAYS));
        this.days = EnumSet.copyOf(days);
    }

    public static DayOfWeekSet of(Set<DayOfWeek> days) {
        return new DayOfWeekSet(days);
    }

    public boolean contains(DayOfWeek day) {
        return days.contains(day);
    }

    public Set<DayOfWeek> getDays() {
        return Collections.unmodifiableSet(days);
    }
}
