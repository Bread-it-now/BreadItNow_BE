package com.breaditnow.reservation.application;

import com.breaditnow.common.aop.Authorize;
import com.breaditnow.reservation.adapter.in.resolver.AuthenticatedUser;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.application.dto.internal.OrdererInfo;
import com.breaditnow.reservation.application.dto.request.MyReservationCreateRequest;
import com.breaditnow.reservation.application.event.ReservationCreatedEvent;
import com.breaditnow.reservation.application.factory.ProductFactory;
import com.breaditnow.reservation.application.provider.BakeryProvider;
import com.breaditnow.reservation.application.provider.CustomerProvider;
import com.breaditnow.reservation.application.validator.BakeryValidator;
import com.breaditnow.reservation.domain.model.Orderer;
import com.breaditnow.reservation.domain.model.Reservation;
import com.breaditnow.reservation.domain.model.ReservationProduct;
import com.breaditnow.reservation.domain.model.ReservedBakery;
import com.breaditnow.reservation.domain.port.in.MyCreateReservationUseCase;
import com.breaditnow.reservation.domain.port.out.ReservationEventPort;
import com.breaditnow.reservation.domain.port.out.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.domain.Role.CUSTOMER;

@Service
@RequiredArgsConstructor
public class MyCreateReservationService implements MyCreateReservationUseCase {
    private final ProductFactory productFactory;
    private final CustomerProvider customerProvider;
    private final BakeryProvider bakeryProvider;
    private final BakeryValidator bakeryValidator;
    private final ReservationRepository reservationRepository;
    private final ReservationEventPort reservationEventPort;

    @Override
    @Transactional
    @Authorize(CUSTOMER)
    public Long createReservation(AuthenticatedUser user, MyReservationCreateRequest request) {
        BakeryInfo bakeryInfo = bakeryProvider.provide(request.bakeryId());
        bakeryValidator.validateBakeryForReservation(bakeryInfo);
        ReservedBakery reservedBakery = ReservedBakery.create(bakeryInfo);

        List<ReservationProduct> reservationProducts = productFactory.createFrom(request, bakeryInfo.bakeryId());
        OrdererInfo ordererInfo = customerProvider.provide(user.userId());
        Orderer orderer = Orderer.create(ordererInfo);

        Reservation reservation = new Reservation(orderer, reservedBakery, reservationProducts);

        Reservation savedReservation = reservationRepository.save(reservation);

        publishReservationCreatedEvent(savedReservation, user.userId(), bakeryInfo, reservationProducts);
        return savedReservation.getReservationId();
    }

    private void publishReservationCreatedEvent(Reservation reservation, Long customerId, BakeryInfo bakeryInfo, List<ReservationProduct> reservationProducts) {
        reservationEventPort.publish(new ReservationCreatedEvent(
                reservation.getReservationId(),
                customerId,
                bakeryInfo.ownerId(),
                bakeryInfo.name(),
                reservationProducts.stream().map(ReservationProduct::getProductName).toList(),
                reservation.getTotalPrice().getAmount()
        ));
    }
}
