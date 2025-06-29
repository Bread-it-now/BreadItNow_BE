package com.breaditnow.reservation.application.strategy;

import com.breaditnow.common.domain.Role;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.domain.model.Reservation;

public interface ReservationCancelStrategy {
    void checkAuthority(AuthenticatedUser user, Reservation reservation);
    Role getRole();
}
