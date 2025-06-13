package com.breaditnow.customer.reservation.infrastructure.jpa.dto;

import com.breaditnow.customer.bakery.infrastructure.jpa.BakeryEntity;
import com.breaditnow.customer.reservation.infrastructure.jpa.ReservationEntity;

public record ReservationWithBakery(BakeryEntity bakery, ReservationEntity reservation) {}
