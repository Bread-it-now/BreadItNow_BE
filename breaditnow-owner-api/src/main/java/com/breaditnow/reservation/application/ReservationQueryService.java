package com.breaditnow.reservation.application;

import com.breaditnow.common.domain.ReservationStatus;
import com.breaditnow.owner.application.OwnerDomainProvider;
import com.breaditnow.reservation.application.dto.response.ReservationListResponse;
import com.breaditnow.reservation.application.port.in.GetReservationListUseCase;
import com.breaditnow.reservation.application.port.out.CustomerProfileProviderPort;
import com.breaditnow.reservation.infrastructure.adapter.out.persistence.jpa.ReservationViewRepository;
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
