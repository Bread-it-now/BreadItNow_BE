package com.breaditnow.notification.application;

import com.breaditnow.notification.adapter.in.dto.ReservationCreatedEvent;
import com.breaditnow.notification.domain.port.out.FcmPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FcmPort fcmPort;

    public void sendReservationNotification(ReservationCreatedEvent event) {

    }
}
