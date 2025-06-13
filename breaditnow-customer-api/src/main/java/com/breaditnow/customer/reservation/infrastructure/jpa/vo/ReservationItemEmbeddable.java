package com.breaditnow.customer.reservation.infrastructure.jpa.vo;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.reservation.domain.ReservationProducts;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationItemEmbeddable {
    private Long productId;
    private String productName;
    private String productImageUrl;
    private Integer productPrice;
    private Integer quantity;
    private Integer totalPrice;

    public static ReservationItemEmbeddable from(ReservationProducts reservationProducts) {
        return new ReservationItemEmbeddable(
            reservationProducts.getProductId(),
            reservationProducts.getProductName(),
            reservationProducts.getProductImage(),
            reservationProducts.getPrice().getAmount(),
            reservationProducts.getQuantity(),
            reservationProducts.getTotalPrice().getAmount()
        );
    }

    public ReservationProducts toDomain() {
        return new ReservationProducts(
            productId,
            productName,
            productImageUrl,
            new Money(productPrice),
            quantity
        );
    }
}
