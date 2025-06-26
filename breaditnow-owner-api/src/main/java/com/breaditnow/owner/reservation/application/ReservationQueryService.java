package com.breaditnow.owner.reservation.application;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.owner.owner.application.OwnerDomainProvider;
import com.breaditnow.owner.reservation.application.dto.response.ReservationListResponse;
import com.breaditnow.owner.reservation.application.port.in.GetReservationListUseCase;
import com.breaditnow.owner.reservation.application.port.out.CustomerProfileProviderPort;
import com.breaditnow.owner.reservation.infrastructure.adapter.out.persistence.jpa.ReservationViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryService implements GetReservationListUseCase {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ReservationViewRepository reservationViewRepo;
    private final CustomerProfileProviderPort customerProfileProvider;


    @Override
    public ReservationListResponse getReservationList(Long ownerId, Long bakeryId, ReservationStatus status, Pageable pageable) {
        return null;
    }
}
