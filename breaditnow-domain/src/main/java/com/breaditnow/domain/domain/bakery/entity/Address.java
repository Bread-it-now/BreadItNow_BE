package com.breaditnow.domain.domain.bakery.entity;

import com.breaditnow.domain.domain.region.entity.RegionPK;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@ToString
@Embeddable
public class Address {
    private String sidoCode;
    private String gugunCode;
    private String dongCode;

    private Double latitude;

    private Double longitude;

    @Column(name = "address_description")
    private String description;

    public Address(RegionPK regionPK, String description) {
        this.sidoCode = regionPK.getSidoCode();
        this.gugunCode = regionPK.getGugunCode();
        this.dongCode = regionPK.getDongCode();
        this.description = description;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
