package com.breaditnow.customer.alert.application.request;

import java.time.LocalTime;
import java.util.Set;

public record GlobalAlertUpdateRequest(Set<String> days, LocalTime startTime, LocalTime endTime) {
}
