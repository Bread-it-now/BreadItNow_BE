package com.breaditnow.domain.domain.region.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class RegionPK implements Serializable {
    private String sidoCode;
    private String gugunCode;
    private String dongCode;

    public RegionPK(String addressCode) {
        this.sidoCode = addressCode.substring(0, 2);
        this.gugunCode = addressCode.substring(2, 5);
        this.dongCode = addressCode.substring(5, 8);
    }
}
