package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.common.exception.CustomerErrorCode;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.application.port.out.CustomerRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositoryPort customerRepositoryPort;

    public Customer loadCustomer(Long customerId) {
        return customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new CustomerException(CustomerErrorCode.CUSTOMER_NOT_FOUND));
    }
}
