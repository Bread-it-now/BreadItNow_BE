package com.breaditnow.customer.reservation.application.event;

import com.breaditnow.customer.reservation.domain.event.ReservationStatusChangedEvent;
import com.breaditnow.customer.reservation.domain.port.SaveReservationStatusHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationStatusHistoryEventHandler {
    private final SaveReservationStatusHistoryPort saveReservationStatusHistoryPort;

    @EventListener
    @Transactional
    public void handleReservationEnrolled(ReservationStatusChangedEvent event) {
        saveReservationStatusHistoryPort.save(event.getReservationId(), event.getPreviousStatus(), event.getCurrentStatus());
    }
}
