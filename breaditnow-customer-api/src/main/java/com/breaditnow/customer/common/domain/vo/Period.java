package com.breaditnow.customer.common.domain.vo;

import com.breaditnow.customer.common.exception.CustomerException;

import java.time.LocalDate;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_PERIOD_VALUE;

public enum Period {
    DAILY, WEEKLY, MONTHLY;

    public static Period of(String period) {
        if (period == null) return null;

        try {
            return Period.valueOf(period.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CustomerException(INVALID_PERIOD_VALUE);
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
