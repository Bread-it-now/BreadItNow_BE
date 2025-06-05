package com.breaditnow.customer.common.infrastructure.jpa;

import com.breaditnow.customer.common.domain.ReleaseTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class ReleaseTimesConverter implements AttributeConverter<List<ReleaseTime>, String> {
    private static final String DELIMITER = ";";

    @Override
    public String convertToDatabaseColumn(List<ReleaseTime> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }

        return attribute.stream()
                .map(releaseTime -> releaseTime.toLocalTime().format(ReleaseTime.FORMATTER))
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<ReleaseTime> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.stream(dbData.split(DELIMITER))
                .map(ReleaseTime::of)
                .toList();
    }
}

