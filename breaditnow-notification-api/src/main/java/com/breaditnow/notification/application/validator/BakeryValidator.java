package com.breaditnow.notification.application.validator;


import com.breaditnow.common.exception.NotificationException;
import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.application.internal.BakeryInfo;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.NotificationErrorCode.FORBIDDEN_ACCESS;
import static com.breaditnow.common.exception.NotificationErrorCode.UNAUTHORIZED_ACCESS;

@Component
public class BakeryValidator {
    public void validateOwner(BakeryInfo bakeryInfo, AuthenticatedUser user) {
        if (!user.isOwner()) {
            throw new NotificationException(FORBIDDEN_ACCESS);
        }
        if (!bakeryInfo.ownerId().equals(user.userId())) {
            throw new NotificationException(UNAUTHORIZED_ACCESS);
        }
    }
}
