package com.breaditnow.customer.alert.application.response;

import com.breaditnow.domain.domain.alert.entity.CustomerAlertSetting;

import java.util.List;

public record CustomerDoNotDisturbResponse(
        boolean active,
        List<String> days,
        String startTime,
        String endTime
) {
    public static CustomerDoNotDisturbResponse of(CustomerAlertSetting setting) {
        return new CustomerDoNotDisturbResponse(
                setting.isActive(),
                setting.getDoNotDisturbDays(),
                setting.getDoNotDisturbStartTime(),
                setting.getDoNotDisturbEndTime()
        );
    }
}
