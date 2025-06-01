package com.breaditnow.customer.alert.application.request;

import org.springframework.cglib.core.Local;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public record CustomerDoNotDisturbUpdateRequest(Set<String> days, LocalTime startTime, LocalTime endTime) {
}
