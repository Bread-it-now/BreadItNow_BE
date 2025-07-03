package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.application.event.ReservationStatusChangedEvent;

public interface ReservationEventPort {
    void publish(ReservationStatusChangedEvent event);
}
