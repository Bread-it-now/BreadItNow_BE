package com.breaditnow.owner.reservation.application.port.in;


import com.breaditnow.owner.reservation.application.dto.event.ReservationCreatedEvent;

public interface ReservationViewSynchronizationUseCase {
    void createReservationView(ReservationCreatedEvent event);
}
