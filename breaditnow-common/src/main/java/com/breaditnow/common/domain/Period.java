package com.breaditnow.common.domain;

import com.breaditnow.common.exception.BreaditnowException;

import java.time.LocalDate;

import static com.breaditnow.common.exception.CommonErrorCode.INVALID_PERIOD_VALUE;

public enum Period {
    DAILY, WEEKLY, MONTHLY;

    public static Period of(String period) {
        if (period == null) return null;

        try {
            return Period.valueOf(period.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BreaditnowException(INVALID_PERIOD_VALUE);
        }
    }

    public LocalDate getStartDate(LocalDate endDate) {
        return switch (this) {
            case DAILY -> endDate.minusDays(1);
            case WEEKLY -> endDate.minusDays(7);
            case MONTHLY -> endDate.minusDays(30);
        };
    }
}
