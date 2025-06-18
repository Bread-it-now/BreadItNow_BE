package com.breaditnow.owner.common.jpa;

import com.breaditnow.owner.common.domain.DailyTime;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class DailyTimeListConverter implements AttributeConverter<List<DailyTime>, String> {
    private static final String DELIMITER = ";";

    @Override
    public String convertToDatabaseColumn(List<DailyTime> dailyTimes) {
        if (dailyTimes == null || dailyTimes.isEmpty()) {
            return null;
        }

        return dailyTimes.stream()
                .map(DailyTime::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<DailyTime> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(dbData.split(DELIMITER))
                .map(DailyTime::of)
                .collect(Collectors.toList());
    }
}
