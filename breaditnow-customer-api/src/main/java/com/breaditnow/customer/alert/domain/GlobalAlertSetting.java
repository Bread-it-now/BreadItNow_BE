package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Builder;
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

    @Builder
    private GlobalAlertSetting(DayOfWeekSet days, ReleaseTime startTime, ReleaseTime endTime, boolean active) {
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = active;
    }

    public static GlobalAlertSetting of(Set<String> days, LocalTime startTime, LocalTime endTime, boolean active) {
        requireValid(startTime, t -> t.isAfter(endTime), () -> new CustomerException(INVALID_TIME_RANGE));
        return builder()
                .days(DayOfWeekSet.of(days))
                .startTime(ReleaseTime.of(startTime))
                .endTime(ReleaseTime.of(endTime))
                .active(active)
                .build();
    }

    public void activate() {
        if (active) throw new CustomerException(ALREADY_ACTIVE);
        this.active = true;
    }

    public void deactivate() {
        if (!active) throw new CustomerException(ALREADY_INACTIVE);
        this.active = false;
    }

    public boolean isWithin(LocalDateTime now) {
        if(!active) return false;

        DayOfWeek today = now.getDayOfWeek();
        if(!days.contains(today)) return false;

        LocalTime currentTime = now.toLocalTime();
        return !currentTime.isBefore(startTime.toLocalTime()) && !currentTime.isAfter(endTime.toLocalTime());
    }
}
