package com.breaditnow.reservation.domain.port.out;

import com.breaditnow.reservation.application.dto.internal.OrdererInfo;

import java.util.Optional;

public interface CustomerApiPort {
    Optional<String> findFcmTokenById(Long customerId);
    Optional<OrdererInfo> findCustomerInfoById(Long customerId);
}
