package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.common.event.NotificationRequiredEvent;

public interface NotificationEventPort {
    void publish(NotificationRequiredEvent event);
}
