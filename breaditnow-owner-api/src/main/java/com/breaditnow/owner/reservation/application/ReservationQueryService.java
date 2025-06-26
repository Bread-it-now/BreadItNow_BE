package com.breaditnow.owner.reservation.application;

import com.breaditnow.owner.owner.application.OwnerDomainProvider;
import com.breaditnow.owner.reservation.application.dto.response.ReservationListResponse;
import com.breaditnow.owner.reservation.application.port.in.GetReservationListUseCase;
import com.breaditnow.owner.reservation.application.port.out.CustomerProfileProviderPort;
import com.breaditnow.owner.reservation.domain.CustomerProfile;
import com.breaditnow.owner.reservation.infrastructure.adapter.out.persistence.jpa.ReservationViewEntity;
import com.breaditnow.owner.reservation.infrastructure.adapter.out.persistence.jpa.ReservationViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryService implements GetReservationListUseCase {
    private final OwnerDomainProvider ownerDomainProvider;
    private final ReservationViewRepository reservationViewRepo;
    private final CustomerProfileProviderPort customerProfileProvider;

    @Override
    public ReservationListResponse getReservationList(Long ownerId, Long bakeryId, String status, Pageable pageable) {
        ownerDomainProvider.getValidatedBakery(ownerId, bakeryId);

        Page<ReservationViewEntity> reservationPage = "ALL".equalsIgnoreCase(status)
                ? reservationViewRepo.findByBakeryId(bakeryId, pageable)
                : reservationViewRepo.findByBakeryIdAndStatus(bakeryId, status, pageable);

        List<Long> customerIds = reservationPage.getContent().stream()
                .map(ReservationViewEntity::getCustomerId).distinct().toList();

        Map<Long, CustomerProfile> customerProfileMap = customerProfileProvider.getCustomerProfileMap(customerIds);

        return ReservationListResponse.of(reservationPage, customerProfileMap);
    }
}
