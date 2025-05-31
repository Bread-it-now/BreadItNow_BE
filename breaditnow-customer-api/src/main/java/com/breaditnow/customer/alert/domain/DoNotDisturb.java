package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.breaditnow.customer.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.*;

@Getter
@EqualsAndHashCode
public class DoNotDisturb {
    private boolean active;
    private DayOfWeekSet days;
    private ReleaseTime startTime;
    private ReleaseTime endTime;

    public DoNotDisturb(DayOfWeekSet days, ReleaseTime startTime, ReleaseTime endTime) {
        requireValid(startTime, t -> false, () -> new CustomerException(INVALID_START_TIME));
        requireValid(endTime, t -> false, () -> new CustomerException(INVALID_END_TIME));
        requireValid(startTime, t -> t.compareTo(endTime) > 0, () -> new CustomerException(INVALID_TIME_RANGE));
        requireValid(days, d -> d.days().isEmpty(), () -> new CustomerException(INVALID_DND_DAYS));
        this.days = days;
        this.startTime = startTime;
        this.endTime = endTime;
        this.active = true;
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
