package com.breaditnow.customer.customer.domain.port.in;

import com.breaditnow.customer.customer.domain.model.Customer;

import java.util.Optional;

public interface GetCustomerInfoUseCase {
    Optional<Customer> findById(Long customerId);
}
