package com.breaditnow.reservation.application.provider;

import com.breaditnow.common.exception.ReservationErrorCode;
import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.application.dto.internal.BakeryInfo;
import com.breaditnow.reservation.domain.port.out.OwnerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BakeryProvider {
    private final OwnerApiPort ownerApiPort;

    public BakeryInfo provide(Long bakeryId) {
        return ownerApiPort.findBakeryInfoById(bakeryId)
                .orElseThrow(() -> new ReservationException(ReservationErrorCode.BAKERY_NOT_FOUND));
    }
}
