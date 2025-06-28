package com.breaditnow.reservation.application.service.strategy;

import com.breaditnow.common.domain.Role;
import com.breaditnow.common.security.AuthenticatedUser;
import com.breaditnow.reservation.domain.model.Reservation;

public interface ReservationCancelStrategy {
    void checkAuthority(AuthenticatedUser user, Reservation reservation);
    Role getRole();
}
