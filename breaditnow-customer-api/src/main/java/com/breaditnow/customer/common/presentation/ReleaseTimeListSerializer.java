package com.breaditnow.customer.common.presentation;

import com.breaditnow.customer.common.domain.ReleaseTime;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class ReleaseTimeListSerializer extends JsonSerializer<List<ReleaseTime>> {
    @Override
    public void serialize(List<ReleaseTime> releaseTimes, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (releaseTimes == null) {
            gen.writeStartArray();
            gen.writeEndArray();
            return;
        }

        gen.writeStartArray();
        for (ReleaseTime releaseTime : releaseTimes) {
            gen.writeString(releaseTime.toString());
        }
        gen.writeEndArray();
    }
}

