package com.breaditnow.product.domain;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.exception.OwnerException;

import static com.breaditnow.common.exception.OwnerErrorCode.PRODUCT_ALREADY_IN_SAME_STATUS;
import static com.breaditnow.product.domain.ProductStatus.SOLD_OUT;

public record SalesPolicy(
        Money price,
        Integer stock,
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
