package com.breaditnow.customer.customer.domain.port.out;

import com.breaditnow.customer.customer.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(Long id);
    Customer getCustomer(Long id);
    Boolean isExistNickName(String nickname);
    Customer save(Customer customer);
    List<Customer> findAllByIdIn(List<Long> customerIds);
}
