package com.breaditnow.reservation.application.service.strategy;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.common.security.AuthenticatedUser;
import com.breaditnow.reservation.domain.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.ReservationErrorCode.UNAUTHORIZED_RESERVATION_CANCEL;

@Component
@RequiredArgsConstructor
public class CustomerCancelStrategy implements ReservationCancelStrategy {
    @Override
    public void checkAuthority(AuthenticatedUser user, Reservation reservation) {
        if (!reservation.getCustomerId().equals(user.userId())) {
            throw new ReservationException(UNAUTHORIZED_RESERVATION_CANCEL);
        }
    }

    @Override
    public Role getRole() {
        return Role.CUSTOMER;
    }
}
