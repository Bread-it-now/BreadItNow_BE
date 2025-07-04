package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.common.event.NotificationSendRequestedEvent;

public interface NotificationEventPort {
    void publish(NotificationSendRequestedEvent event);
}
