package com.breaditnow.customer.alert.application.request;

import java.time.LocalTime;
import java.util.Set;

public record GlobalAlertSettingUpdateRequest(Set<String> days, LocalTime startTime, LocalTime endTime) {
}
