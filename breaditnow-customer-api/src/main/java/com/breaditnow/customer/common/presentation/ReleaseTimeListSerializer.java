package com.breaditnow.customer.common.presentation;

import com.breaditnow.customer.common.domain.DailyTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class ReleaseTimeListSerializer extends JsonSerializer<List<DailyTime>> {
    @Override
    public void serialize(List<DailyTime> dailyTimes, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (dailyTimes == null) {
            gen.writeStartArray();
            gen.writeEndArray();
            return;
        }

        gen.writeStartArray();
        for (DailyTime dailyTime : dailyTimes) {
            gen.writeString(dailyTime.toString());
        }
        gen.writeEndArray();
    }
}

