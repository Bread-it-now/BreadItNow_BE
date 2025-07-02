package com.breaditnow.reservation.application.validator;

import com.breaditnow.common.domain.OperatingStatus;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.ReservationErrorCode.*;

@Component
public class BakeryValidator {
    public void validateBakeryForReservation(BakeryInfo bakeryInfo) {
        if (bakeryInfo.deleted()) {
            throw new ReservationException(BAKERY_IS_DELETED);
        }
        if (bakeryInfo.operatingStatus() != OperatingStatus.OPEN) {
            throw new ReservationException(BAKERY_IS_NOT_OPENED);
        }
    }

    public void validateOwner(BakeryInfo bakeryInfo, AuthenticatedUser user) {
        if (!user.isOwner()) {
            throw new ReservationException(FORBIDDEN_ACCESS);
        }
        if (!bakeryInfo.ownerId().equals(user.userId())) {
            throw new ReservationException(UNAUTHORIZED_ACCESS);
        }
    }
}
