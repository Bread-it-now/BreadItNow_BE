package com.breaditnow.alert.domain;

import com.breaditnow.common.domain.DailyTime;
import com.breaditnow.common.exception.CustomerException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static com.breaditnow.common.exception.CustomerErrorCode.INVALID_TIME_RANGE;
import static com.breaditnow.common.util.ValidationUtils.requireValid;

@Getter
@EqualsAndHashCode
public class GlobalAlertSetting {
    private DayOfWeekSet days;
    private DailyTime startTime;
    private DailyTime endTime;
    private boolean active;

    @Builder
    private GlobalAlertSetting(DayOfWeekSet days, DailyTime startTime, DailyTime endTime, boolean active) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public static GlobalAlertSetting create(Set<String> days, LocalTime startTime, LocalTime endTime, boolean active) {
        requireValid(startTime, t -> t.isAfter(endTime), () -> new CustomerException(INVALID_TIME_RANGE));
        return new GlobalAlertSetting(DayOfWeekSet.of(days), DailyTime.of(startTime), DailyTime.of(endTime), active);
    }

    public void toggle() {
        this.active = !this.active;
    }

    public boolean isWithin(LocalDateTime now) {
        if(!active) return false;

        DayOfWeek today = now.getDayOfWeek();
        if(!days.contains(today)) return false;

        LocalTime currentTime = now.toLocalTime();
        return !currentTime.isBefore(startTime.toLocalTime()) && !currentTime.isAfter(endTime.toLocalTime());
    }
}
