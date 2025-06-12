package com.breaditnow.customer.bakery.infrastructure.jpa;

import com.breaditnow.customer.bakery.domain.BakeryFavorite;
import com.breaditnow.domain.global.entity.BaseEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "customer_bakery_favorite")
public class BakeryFavoriteEntity extends BaseEntity {
    @EmbeddedId
    private BakeryFavoriteEntityId id;
    private boolean isActive;

    public BakeryFavoriteEntity(BakeryFavorite bakeryFavorite) {
        this.id = new BakeryFavoriteEntityId(bakeryFavorite.getCustomerId(), bakeryFavorite.getBakeryId());
        this.isActive = bakeryFavorite.isActive();
    }

    public BakeryFavorite toDomain() {
        return BakeryFavorite.builder()
                .customerId(id.getCustomerId())
                .bakeryId(id.getBakeryId())
                .isActive(isActive)
                .build();
    }
}
