package com.breaditnow.customer.customer.domain.port;

import com.breaditnow.customer.customer.domain.Customer;

import java.util.Optional;

public interface LoadCustomerPort {
    Optional<Customer> findById(Long id);
    Customer getCustomer(Long id);
    Boolean isExistNickName(String nickname);
}
