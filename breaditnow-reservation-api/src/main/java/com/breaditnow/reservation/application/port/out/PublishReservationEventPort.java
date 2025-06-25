package com.breaditnow.reservation.application.port.out;

import com.breaditnow.reservation.application.dto.event.ReservationCreatedEvent;

public interface PublishReservationEventPort {
    void publishReservationCreated(ReservationCreatedEvent event);
//    void publishReservationConfirmed(ReservationConfirmedEvent event);
//    void publishReservationRejected(ReservationRejectedEvent event);
//    void publishReservationCanceled(ReservationCanceledEvent event);
}
