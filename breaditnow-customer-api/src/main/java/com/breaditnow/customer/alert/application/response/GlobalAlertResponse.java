package com.breaditnow.customer.alert.application.response;

import com.breaditnow.customer.alert.domain.GlobalAlertSetting;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public record GlobalAlertResponse(
        boolean active,
        Set<DayOfWeek> days,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime
) {
    public static GlobalAlertResponse of(GlobalAlertSetting globalAlertSetting) {
        return new GlobalAlertResponse(
                globalAlertSetting.isActive(),
                globalAlertSetting.getDays().days(),
                globalAlertSetting.getStartTime().toLocalTime(),
                globalAlertSetting.getEndTime().toLocalTime()
        );
    }
}
