package com.breaditnow.common.infrastructure.jpa;

import com.breaditnow.common.domain.DailyTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalTime;

@Converter
public class ReleaseTimeConverter implements AttributeConverter<DailyTime, LocalTime> {
    @Override
    public LocalTime convertToDatabaseColumn(DailyTime attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toLocalTime();
    }

    @Override
    public DailyTime convertToEntityAttribute(LocalTime dbData) {
        if (dbData == null) {
            return null;
        }
        return DailyTime.of(dbData);
    }
}