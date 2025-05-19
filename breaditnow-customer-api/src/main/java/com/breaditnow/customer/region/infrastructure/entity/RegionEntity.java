package com.breaditnow.customer.region.infrastructure.entity;

import com.breaditnow.customer.region.core.Region;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "region")
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
public class RegionEntity {
    @EmbeddedId
    private RegionIdEntity id;

    private String sidoName;

    private String gugunName;

    private String dongName;

    public Region toRegion() {
        return Region.builder()
                .id(id.toRegionId())
                .sidoName(sidoName)
                .gugunName(gugunName)
                .dongName(dongName)
                .build();
    }
}
