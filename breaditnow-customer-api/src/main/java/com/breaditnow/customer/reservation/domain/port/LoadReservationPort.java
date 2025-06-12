package com.breaditnow.customer.reservation.domain.port;

import java.time.LocalDate;

public interface LoadReservationPort {
    Long findLatestReservationNumberForBakeryToday(Long bakeryId, LocalDate today);
}
