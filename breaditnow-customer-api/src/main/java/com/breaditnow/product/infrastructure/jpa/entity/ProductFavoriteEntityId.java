package com.breaditnow.product.infrastructure.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductFavoriteEntityId implements Serializable {
    private Long customerId;
    private Long productId;
}