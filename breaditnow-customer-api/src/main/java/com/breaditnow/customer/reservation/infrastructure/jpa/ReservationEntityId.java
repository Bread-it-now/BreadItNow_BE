package com.breaditnow.customer.reservation.infrastructure.jpa;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ReservationEntityId {
    private Long ordererId;
    private Long bakeryId;
}
