package com.breaditnow.bakery.infrastructure.jpa;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BakeryFavoriteEntityId implements Serializable {
    private Long customerId;
    private Long bakeryId;
}
