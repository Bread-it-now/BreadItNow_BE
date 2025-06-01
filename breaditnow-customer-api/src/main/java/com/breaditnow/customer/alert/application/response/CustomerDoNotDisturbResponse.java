package com.breaditnow.customer.alert.application.response;

import com.breaditnow.customer.alert.domain.DayOfWeekSet;
import com.breaditnow.customer.alert.domain.DoNotDisturb;
import com.breaditnow.domain.domain.alert.entity.CustomerAlertSetting;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public record CustomerDoNotDisturbResponse(
        boolean active,
        Set<DayOfWeek> days,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime
) {
    public static CustomerDoNotDisturbResponse of(DoNotDisturb doNotDisturb) {
        return new CustomerDoNotDisturbResponse(
                doNotDisturb.isActive(),
                doNotDisturb.getDays().days(),
                doNotDisturb.getStartTime().toLocalTime(),
                doNotDisturb.getEndTime().toLocalTime()
        );
    }
}
