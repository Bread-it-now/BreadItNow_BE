package com.breaditnow.region.adapter.out.persistence.entity;

import com.breaditnow.region.domain.model.Region;
import jakarta.persistence.Column;
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

    @Column(name = "sido_code", insertable = false, updatable = false)
    private String sidoCode;

    @Column(name = "gugun_code", insertable = false, updatable = false)
    private String gugunCode;

    @Column(name = "dong_code", insertable = false, updatable = false)
    private String dongCode;

    public Region toDomain() {
        return Region.builder()
                .regionCode(regionCode)
                .sidoName(sidoName)
                .gugunName(gugunName)
                .dongName(dongName)
                .build();
    }
}
