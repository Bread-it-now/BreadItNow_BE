package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.request.ReservationPartialApproveRequest;
import com.breaditnow.reservation.application.factory.ProductFactory;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.ReservationProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.application.validator.ReservationValidator;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.port.in.PartialApproveReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.domain.Role.OWNER;

@Service
@RequiredArgsConstructor
@Transactional
public class PartialApproveReservationService implements PartialApproveReservationUseCase {
    private final BakeryProvider bakeryProvider;
    private final ProductFactory productFactory;
    private final BakeryValidator bakeryValidator;
    private final ReservationValidator reservationValidator;
    private final ReservationProvider reservationProvider;
    private final ReservationRepository reservationRepository;

    @Override
    @Authorize(OWNER)
    public void partialApproveReservation(AuthenticatedUser user, Long reservationId, Long bakeryId, ReservationPartialApproveRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(bakeryId);

        bakeryValidator.validateOwner(bakeryInfo, user);
        bakeryValidator.validateBakeryForReservation(bakeryInfo);

        List<ReservationProduct> reservationProducts = productFactory.createFrom(request, bakeryInfo.bakeryId());

        Reservation reservation = reservationProvider.provide(reservationId);
        reservationValidator.validateBakeryMatch(reservation, bakeryId);

        Long newReservationNumber = reservationRepository.getNextReservationNumber(bakeryId);

        reservation.partialApprove(reservationProducts, newReservationNumber);
        reservationRepository.save(reservation);
    }
}
