package com.breaditnow.customer.region.infrastructure.entity;

import com.breaditnow.customer.region.core.Region;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "region")
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
public class RegionEntity {
    @Id
    private String regionCode;

    private String sidoName;
    private String gugunName;
    private String dongName;

    public Region toDomain() {
        return Region.builder()
                .regionCode(regionCode)
                .sidoName(sidoName)
                .gugunName(gugunName)
                .dongName(dongName)
                .build();
    }
}
