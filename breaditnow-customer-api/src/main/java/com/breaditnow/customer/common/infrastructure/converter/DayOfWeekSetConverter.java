package com.breaditnow.customer.common.infrastructure.converter;

import com.breaditnow.customer.alert.domain.DayOfWeekSet;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class DayOfWeekSetConverter implements AttributeConverter<DayOfWeekSet, String> {
    @Override
    public String convertToDatabaseColumn(DayOfWeekSet attribute) {
        if (attribute == null || attribute.days().isEmpty()) {
            return "";
        }
        return attribute.days().stream()
                .map(DayOfWeek::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public DayOfWeekSet convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return DayOfWeekSet.empty();
        }

        Set<DayOfWeek> days = Arrays.stream(dbData.split(","))
                .map(String::trim)
                .filter(token -> !token.isEmpty())
                .map(DayOfWeek::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(DayOfWeek.class)));

        return DayOfWeekSet.of(days);
    }
}
