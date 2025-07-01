package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.response.ReservationPageResponse;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.domain.port.in.QueryReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryReservationService implements QueryReservationUseCase {
    private final BakeryProvider bakeryProvider;
    private final BakeryValidator bakeryValidator;
    private final ReservationRepository reservationRepository;

    @Override
    @Authorize(OWNER)
    public ReservationPageResponse getMyReservations(AuthenticatedUser user, Long bakeryId, Pageable pageable, ReservationStatus status) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);
        bakeryValidator.validateOwner(bakeryInfo, user);
        return ReservationPageResponse.of(reservationRepository.findByBakeryId(bakeryId, pageable, status));
    }
}
