package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.common.exception.CustomerException;
import lombok.Getter;

import static com.breaditnow.customer.common.domain.ValidationUtils.requireValid;
import static com.breaditnow.customer.common.exception.CustomerErrorCode.QUANTITY_POSITIVE;

@Getter
public class ReservationProducts {
    private Long productId;
    private String productName;
    private String productImage;
    private Money price;
    private Integer quantity;
    private Money totalPrice;

    public ReservationProducts(Long productId, String productName, String productImage, Money price, Integer quantity) {
        requireValid(quantity, q -> q <= 0, () -> new CustomerException(QUANTITY_POSITIVE));
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = calculateTotalPrice();
    }

    private Money calculateTotalPrice() {
        return price.multiply(quantity);
    }
}
