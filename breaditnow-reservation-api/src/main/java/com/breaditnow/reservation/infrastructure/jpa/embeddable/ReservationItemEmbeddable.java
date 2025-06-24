package com.breaditnow.reservation.infrastructure.jpa.embeddable;


import com.breaditnow.common.domain.Money;
import com.breaditnow.reservation.domain.ReservationProduct;
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

    public static ReservationItemEmbeddable from(ReservationProduct reservationProduct) {
        return new ReservationItemEmbeddable(
            reservationProduct.getProductId(),
            reservationProduct.getProductName(),
            reservationProduct.getProductImage(),
            reservationProduct.getPrice().getAmount(),
            reservationProduct.getQuantity(),
            reservationProduct.getTotalPrice().getAmount()
        );
    }

    public ReservationProduct toDomain() {
        return new ReservationProduct(
            productId,
            productName,
            productImageUrl,
            new Money(productPrice),
            quantity
        );
    }
}
