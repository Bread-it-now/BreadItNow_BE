package com.breaditnow.common.domain;

import java.time.LocalDate;

public record PeriodRange(LocalDate startDate, LocalDate endDate) {
    public static PeriodRange of(Period period) {
        if(period == null) return null;

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = period.getStartDate(endDate);

        return new PeriodRange(startDate, endDate);
    }
}
