package com.breaditnow.reservation.domain.model;

import com.breaditnow.common.domain.Money;
import com.breaditnow.common.exception.ReservationException;
import lombok.Getter;

import static com.breaditnow.common.exception.ReservationErrorCode.QUANTITY_POSITIVE;
import static com.breaditnow.common.util.ValidationUtils.requireValid;

@Getter
public class ReservationProduct {
    private Long productId;
    private String productName;
    private String productImage;
    private Money price;
    private Integer quantity;
    private Money totalPrice;

    public ReservationProduct(Long productId, String productName, String productImage, Money price, Integer quantity) {
        requireValid(quantity, q -> q <= 0, () -> new ReservationException(QUANTITY_POSITIVE));
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
