package com.breaditnow.product.infrastructure.adapter.out.persistence.jpa;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class DailyTimeListConverter implements AttributeConverter<List<String>, String> {
    private static final String DELIMITER = ";";

    @Override
    public String convertToDatabaseColumn(List<String> dailyTimes) {
        if (dailyTimes == null || dailyTimes.isEmpty()) {
            return null;
        }

        return String.join(DELIMITER, dailyTimes);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(dbData.split(DELIMITER))
                .toList();
    }
}
