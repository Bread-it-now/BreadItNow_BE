package com.breaditnow.customer.alert.infrastructure.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProductAlertEntityId implements Serializable {
    private Long customerId;
    private Long productId;
}

