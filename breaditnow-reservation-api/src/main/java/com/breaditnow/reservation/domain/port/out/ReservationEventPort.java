package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.application.event.ReservationCreatedEvent;

public interface ReservationEventPort {
    void publish(ReservationCreatedEvent event);
}
