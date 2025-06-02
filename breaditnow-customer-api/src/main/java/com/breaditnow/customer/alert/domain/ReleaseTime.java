package com.breaditnow.customer.alert.domain;

import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.breaditnow.customer.common.exception.CustomerErrorCode.INVALID_TIME_FORMAT;

@EqualsAndHashCode
public class ReleaseTime implements Comparable<ReleaseTime> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private final LocalTime time;

    @Builder
    private ReleaseTime(LocalTime time) {
        this.time = time;
    }

    public static ReleaseTime of(String hhmm) {
        try {
            LocalTime lt = LocalTime.parse(hhmm, FORMATTER);
            return new ReleaseTime(lt);
        } catch (DateTimeParseException e) {
            throw new CustomerException(INVALID_TIME_FORMAT);
        }
    }

    public static ReleaseTime of(LocalTime localTime) {
        if (localTime == null) {
            throw new CustomerException(INVALID_TIME_FORMAT);
        }
        return new ReleaseTime(localTime);
    }

    public LocalTime toLocalTime() {
        return time;
    }

    @Override
    public int compareTo(ReleaseTime other) {
        return this.time.compareTo(other.time);
    }
}

