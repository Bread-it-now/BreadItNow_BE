package com.breaditnow.reservation.application.strategy;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.ReservationErrorCode.BAKERY_NOT_FOUND;
import static com.breaditnow.common.exception.ReservationErrorCode.UNAUTHORIZED_RESERVATION_CANCEL;

@Component
@RequiredArgsConstructor
public class OwnerCancelStrategy implements ReservationCancelStrategy {

    private final OwnerApiPort ownerApiPort;

    @Override
    public void checkAuthority(AuthenticatedUser user, Reservation reservation) {
        BakeryInfo bakeryInfo = ownerApiPort.findBakeryById(reservation.getBakeryId())
                .orElseThrow(() -> new ReservationException(BAKERY_NOT_FOUND));

        if (!bakeryInfo.ownerId().equals(user.userId())) {
            throw new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL);
        }
    }

    @Override
    public Role getRole() {
        return Role.OWNER;
    }
}
