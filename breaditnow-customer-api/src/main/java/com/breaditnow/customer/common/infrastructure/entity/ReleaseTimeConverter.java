package com.breaditnow.customer.common.infrastructure.entity;

import com.breaditnow.customer.common.domain.ReleaseTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalTime;

@Converter
public class ReleaseTimeConverter implements AttributeConverter<ReleaseTime, LocalTime> {
    @Override
    public LocalTime convertToDatabaseColumn(ReleaseTime attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toLocalTime();
    }

    @Override
    public ReleaseTime convertToEntityAttribute(LocalTime dbData) {
        if (dbData == null) {
            return null;
        }
        return ReleaseTime.of(dbData);
    }
}