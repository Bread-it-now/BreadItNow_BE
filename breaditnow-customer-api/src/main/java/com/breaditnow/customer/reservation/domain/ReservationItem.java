package com.breaditnow.customer.reservation.domain;

import com.breaditnow.customer.common.domain.Money;
import lombok.Getter;

@Getter
public class ReservationItem {
    private Long productId;
    private String productName;
    private String productImage;
    private Money price;
    private Integer quantity;
    private Money totalPrice;

    public ReservationItem(Long productId, String productName, String productImage, Money price, Integer quantity) {
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
