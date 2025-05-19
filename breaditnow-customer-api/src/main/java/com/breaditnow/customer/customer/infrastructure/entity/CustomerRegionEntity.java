package com.breaditnow.customer.customer.infrastructure.entity;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.region.core.Region;
import com.breaditnow.customer.region.core.RegionId;
import com.breaditnow.customer.region.infrastructure.entity.RegionIdEntity;
import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "customer_region")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class CustomerRegionEntity extends BaseEntity {
    @EmbeddedId
    private CustomerRegionIdEntity id;

    public CustomerRegionEntity(Customer customer, Region region) {
        RegionId regionId = region.getId();
        RegionIdEntity regionIdEntity = new RegionIdEntity(regionId);
        this.id = new CustomerRegionIdEntity(customer.getId(), regionIdEntity);
    }
}
