package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.application.port.out.CustomerRepositoryPort;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.breaditnow.domain.global.exception.DomainErrorCode.CUSTOMER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositoryPort customerRepositoryPort;

    public Customer loadCustomer(Long customerId) {
        return customerRepositoryPort.findById(customerId)
                .orElseThrow(() -> new DomainException(CUSTOMER_NOT_FOUND));
    }
}
