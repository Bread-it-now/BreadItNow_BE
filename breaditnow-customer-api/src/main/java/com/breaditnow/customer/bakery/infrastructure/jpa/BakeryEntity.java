package com.breaditnow.customer.bakery.infrastructure.jpa;

import com.breaditnow.customer.common.domain.vo.GeoPoint;
import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bakery")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BakeryEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long ownerId;
    private boolean isActive;

    private Double latitude;
    private Double longitude;
}
