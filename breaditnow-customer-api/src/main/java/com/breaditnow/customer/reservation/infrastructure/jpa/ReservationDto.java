package com.breaditnow.customer.reservation.infrastructure.jpa;

import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryEntity;
import com.breaditnow.customer.reservation.infrastructure.jpa.entity.ReservationEntity;

public record ReservationDto(BakeryEntity bakery, ReservationEntity reservation) {}
