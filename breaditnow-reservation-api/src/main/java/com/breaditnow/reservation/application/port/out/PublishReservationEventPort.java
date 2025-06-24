package com.breaditnow.reservation.application.port.out;

import com.breaditnow.reservation.application.dto.event.ReservationCanceledEvent;
import com.breaditnow.reservation.application.dto.event.ReservationConfirmedEvent;
import com.breaditnow.reservation.application.dto.event.ReservationCreatedEvent;
import com.breaditnow.reservation.application.dto.event.ReservationRejectedEvent;

public interface PublishReservationEventPort {
    void publishReservationCreated(ReservationCreatedEvent event);
    void publishReservationConfirmed(ReservationConfirmedEvent event);
    void publishReservationRejected(ReservationRejectedEvent event);
    void publishReservationCanceled(ReservationCanceledEvent event);
}
