package com.breaditnow.customer.common.domain;

import com.breaditnow.customer.common.exception.CustomerException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_TIME_FORMAT;

public record DailyTime(LocalTime time) implements Comparable<DailyTime> {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static DailyTime of(String hm) {
        try {
            LocalTime lt = LocalTime.parse(hm, FORMATTER);
            return new DailyTime(lt);
        } catch (DateTimeParseException e) {
            throw new CustomerException(INVALID_TIME_FORMAT);
        }
    }

    public static DailyTime of(LocalTime localTime) {
        if (localTime == null) {
            throw new CustomerException(INVALID_TIME_FORMAT);
        }
        return new DailyTime(localTime);
    }

    public LocalTime toLocalTime() {
        return time;
    }

    @Override
    public int compareTo(DailyTime other) {
        return this.time.compareTo(other.time);
    }

    @Override
    public String toString() {
        return time.format(FORMATTER);
    }
}

