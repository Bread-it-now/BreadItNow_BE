package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.common.exception.CustomerErrorCode;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.customer.domain.model.Customer;
import com.breaditnow.customer.customer.domain.port.out.CustomerRepository;
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
