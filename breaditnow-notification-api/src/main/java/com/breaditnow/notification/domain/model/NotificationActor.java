package com.breaditnow.notification.domain.model;

import com.breaditnow.common.domain.Role;

public record NotificationActor(
        Long userId,
        Role role
) {
}
