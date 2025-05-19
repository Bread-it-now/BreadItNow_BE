package com.breaditnow.customer.customer.infrastructure.entity;

import com.breaditnow.customer.region.infrastructure.entity.RegionIdEntity;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRegionIdEntity {
    private Long customerId;
    private RegionIdEntity regionId;
}
