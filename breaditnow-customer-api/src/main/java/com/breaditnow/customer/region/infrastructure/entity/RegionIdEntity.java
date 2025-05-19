package com.breaditnow.customer.region.infrastructure.entity;

import com.breaditnow.customer.region.core.RegionId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class RegionIdEntity implements Serializable {
    @Column(nullable = false)
    private String sidoCode;

    @Column(nullable = false)
    private String gugunCode;

    @Column(nullable = false)
    private String dongCode;

    public RegionIdEntity(String sidoCode, String gugunCode, String dongCode) {
        this.sidoCode = sidoCode;
        this.gugunCode = gugunCode;
        this.dongCode = dongCode;
    }

    public RegionIdEntity(RegionId regionId) {
        this(regionId.sidoCode(), regionId.gugunCode(), regionId.dongCode());
    }

    public RegionId toRegionId() {
        return new RegionId(sidoCode, gugunCode, dongCode);
    }
}
