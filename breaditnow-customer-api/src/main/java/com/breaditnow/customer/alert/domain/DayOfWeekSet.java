package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.breaditnow.customer.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.*;

public record DayOfWeekSet(Set<DayOfWeek> days) {
    @Builder
    public DayOfWeekSet {
    }

    public static DayOfWeekSet of(Set<String> days) {
        requireValid(days, d -> d == null || d.isEmpty(), () -> new CustomerException(EMPTY_DND_DAYS));
        try {
            return builder().
                    days(days.stream()
                            .map(String::toUpperCase)
                            .map(DayOfWeek::valueOf)
                            .collect(Collectors.collectingAndThen(
                                            Collectors.toSet(),
                                            EnumSet::copyOf
                                    )
                            )
                    ).build();
        } catch (IllegalArgumentException e) {
            throw new CustomerException(INVALID_DND_DAY_VALUE);
        }
    }

    public static DayOfWeekSet empty() {
        return builder().days(Set.of()).build();
    }

    public boolean contains(DayOfWeek day) {
        return days.contains(day);
    }

    @Override
    public Set<DayOfWeek> days() {
        return Collections.unmodifiableSet(days);
    }
}