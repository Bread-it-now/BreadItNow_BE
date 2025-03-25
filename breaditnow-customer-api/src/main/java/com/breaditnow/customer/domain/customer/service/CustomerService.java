package com.breaditnow.customer.domain.customer.service;

import com.breaditnow.customer.domain.customer.controller.res.CustomerInfoResponse;
import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerInfoResponse getCustomerInfo(Long customerId) {
        Customer customer = customerRepository.getById(customerId);

        return CustomerInfoResponse.of(customer);
    }
}
