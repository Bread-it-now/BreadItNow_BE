package com.breaditnow.customer.reservation.infrastructure.jpa.vo;

import com.breaditnow.customer.common.domain.Money;
import com.breaditnow.customer.reservation.domain.ReservationItem;
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

    public static ReservationItemEmbeddable from(ReservationItem reservationItem) {
        return new ReservationItemEmbeddable(
            reservationItem.getProductId(),
            reservationItem.getProductName(),
            reservationItem.getProductImage(),
            reservationItem.getPrice().getAmount(),
            reservationItem.getQuantity(),
            reservationItem.getTotalPrice().getAmount()
        );
    }

    public ReservationItem toDomain() {
        return new ReservationItem(
            productId,
            productName,
            productImageUrl,
            new Money(productPrice),
            quantity
        );
    }
}
