package com.breaditnow.reservation.application.port.in;


import com.breaditnow.reservation.application.dto.event.ReservationCreatedEvent;

public interface ReservationViewSynchronizationUseCase {
    void createReservationView(ReservationCreatedEvent event);
}
