package com.breaditnow.customer.reservation.domain.port;

import com.breaditnow.customer.reservation.domain.Orderer;

public interface LoadOrdererPort {
    Orderer getOrderer(Long ordererId);
}
