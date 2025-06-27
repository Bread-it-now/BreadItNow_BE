package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.domain.model.Customer;
import com.breaditnow.customer.customer.domain.port.in.GetCustomerInfoUseCase;
import com.breaditnow.customer.customer.domain.port.out.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerQueryService implements GetCustomerInfoUseCase {
    private final CustomerRepository customerRepository;

    @Override
    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }
}
