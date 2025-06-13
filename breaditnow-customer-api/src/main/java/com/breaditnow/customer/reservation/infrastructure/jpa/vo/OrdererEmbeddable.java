package com.breaditnow.customer.reservation.infrastructure.jpa.vo;

import com.breaditnow.customer.reservation.domain.Orderer;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrdererEmbeddable {
    private Long ordererId;
    private String nickname;
    private String phoneNumber;

    public static OrdererEmbeddable from(Orderer orderer) {
        return new OrdererEmbeddable(
            orderer.getOrdererId(),
            orderer.getOrdererName(),
            orderer.getOrdererPhoneNumber()
        );
    }

    public Orderer toDomain() {
        return new Orderer(ordererId, nickname, phoneNumber);
    }
}
