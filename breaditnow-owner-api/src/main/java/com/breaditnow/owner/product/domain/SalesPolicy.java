package com.breaditnow.owner.product.domain;

import com.breaditnow.owner.common.domain.Money;
import com.breaditnow.owner.global.exception.OwnerException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

import static com.breaditnow.owner.global.exception.OwnerErrorCode.PRODUCT_ALREADY_IN_SAME_STATUS;
import static com.breaditnow.owner.product.domain.ProductStatus.SOLD_OUT;
import static jakarta.persistence.EnumType.STRING;

@Embeddable
public record SalesPolicy(
        Money price,
        Integer stock,
        @Enumerated(STRING)
        ProductStatus status
) {
    public static SalesPolicy create(Integer price){
        return new SalesPolicy(new Money(price), 0, SOLD_OUT);
    }

    public SalesPolicy withStock(Integer stock) {
        ProductStatus currentStatus = this.status;
        ProductStatus newStatus = currentStatus;

        if (stock == 0) {
            newStatus = SOLD_OUT;
        } else if (currentStatus == SOLD_OUT && stock > 0) {
            newStatus = ProductStatus.FOR_SALE;
        }
        return new SalesPolicy(this.price, stock, newStatus);
    }

    public SalesPolicy withStatus(ProductStatus newStatus) {
        if (this.status == newStatus) {
            throw new OwnerException(PRODUCT_ALREADY_IN_SAME_STATUS);
        }
        return new SalesPolicy(this.price, this.stock, newStatus);
    }
}
