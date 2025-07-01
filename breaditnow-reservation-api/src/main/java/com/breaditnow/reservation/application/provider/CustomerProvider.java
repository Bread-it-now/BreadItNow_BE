package com.breaditnow.reservation.application.provider;

import com.breaditnow.common.exception.ReservationException;
import com.breaditnow.reservation.application.dto.internal.OrdererInfo;
import com.breaditnow.reservation.domain.port.out.CustomerApiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.breaditnow.common.exception.ReservationErrorCode.CUSTOMER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CustomerProvider {
    private final CustomerApiPort customerApiPort;

    public OrdererInfo provide(Long customerId) {
        return customerApiPort.findCustomerInfoById(customerId)
                .orElseThrow(() -> new ReservationException(CUSTOMER_NOT_FOUND));
    }
}
