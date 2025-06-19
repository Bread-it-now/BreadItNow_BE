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

    public SalesPolicy withStock(Integer stock) {
        ProductStatus currentStatus = this.status;
        ProductStatus newStatus = currentStatus;

        if (stock == 0) {
            newStatus = ProductStatus.SOLD_OUT;
        } else if (currentStatus == ProductStatus.SOLD_OUT && stock > 0) {
            newStatus = ProductStatus.FOR_SALE;
        }
        return new SalesPolicy(this.price, stock, newStatus);
    }

    public SalesPolicy withStatus(ProductStatus newStatus) {
        return new SalesPolicy(this.price, this.stock, newStatus);
    }
}
