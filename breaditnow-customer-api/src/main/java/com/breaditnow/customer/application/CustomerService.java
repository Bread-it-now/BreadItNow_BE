package com.breaditnow.customer.application;

import com.breaditnow.common.exception.CustomerErrorCode;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.customer.domain.model.Customer;
import com.breaditnow.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer loadCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerException(CustomerErrorCode.CUSTOMER_NOT_FOUND));
    }
}
