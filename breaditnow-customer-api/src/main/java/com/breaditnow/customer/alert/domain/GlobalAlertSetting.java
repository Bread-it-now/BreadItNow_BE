package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.domain.ReleaseTime;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import static com.breaditnow.customer.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.*;

@Getter
@EqualsAndHashCode
public class GlobalAlertSetting {
    private boolean active;
    private DayOfWeekSet days;
    private ReleaseTime startTime;
    private ReleaseTime endTime;

    private GlobalAlertSetting(DayOfWeekSet days, ReleaseTime startTime, ReleaseTime endTime, boolean active) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public static GlobalAlertSetting of(Set<String> days, LocalTime startTime, LocalTime endTime, boolean active) {
        requireValid(startTime, t -> t.isAfter(endTime), () -> new CustomerException(INVALID_TIME_RANGE));
        return new GlobalAlertSetting(DayOfWeekSet.of(days), ReleaseTime.of(startTime), ReleaseTime.of(endTime), active);
    }

    public static GlobalAlertSetting from(DayOfWeekSet days, ReleaseTime startTime, ReleaseTime endTime, boolean active) {
        return new GlobalAlertSetting(days, startTime, endTime, active);
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
