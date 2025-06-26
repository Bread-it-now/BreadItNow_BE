package com.breaditnow.customer.customer.application.port.out;

import com.breaditnow.customer.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
    Optional<Customer> findById(Long id);
    Customer getCustomer(Long id);
    Boolean isExistNickName(String nickname);
    Customer save(Customer customer);
    List<Customer> findAllByIdIn(List<Long> customerIds);
}
