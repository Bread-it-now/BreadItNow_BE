package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.common.domain.Money;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

import static jakarta.persistence.EnumType.STRING;

@Embeddable
public record SalesPolicy(
        Money price,
        Integer stock,
        @Enumerated(STRING)
        ProductStatus status
) {
    public static SalesPolicy create(Integer price){
        return new SalesPolicy(new Money(price), 0, ProductStatus.FOR_SALE);
    }
}
