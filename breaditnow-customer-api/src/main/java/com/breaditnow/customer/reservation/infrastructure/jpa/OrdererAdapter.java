package com.breaditnow.customer.reservation.infrastructure.jpa;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.infrastructure.CustomerAdapter;
import com.breaditnow.customer.reservation.domain.Orderer;
import com.breaditnow.customer.reservation.domain.port.LoadOrdererPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrdererAdapter implements LoadOrdererPort {
    private final CustomerAdapter customerAdapter;

    @Override
    public Orderer getOrderer(Long ordererId) {
        Customer customer = customerAdapter.getCustomer(ordererId);
        return new Orderer(customer.getId(), customer.getNickname(), customer.getPhone());
    }
}
