package com.breaditnow.customer.domain.common.req;

import com.breaditnow.domain.global.dto.GeoPoint;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record GeoPointRequest(
        @DecimalMin(value = "-90.0", message = "위도는 -90 이상이어야 합니다.")
        @DecimalMax(value = "90.0", message = "위도는 90 이하이어야 합니다.")
        @Schema(description = "위도(latitude)", example = "37.5665")
        Double latitude,

        @DecimalMin(value = "-180.0", message = "경도는 -180 이상이어야 합니다.")
        @DecimalMax(value = "180.0", message = "경도는 180 이하이어야 합니다.")
        @Schema(description = "경도(longitude)", example = "126.9780")
        Double longitude
) {
    public GeoPoint toEntity() {
        if (latitude == null || longitude == null) {
            return null;
        }

        return new GeoPoint(latitude, longitude);
    }
}
