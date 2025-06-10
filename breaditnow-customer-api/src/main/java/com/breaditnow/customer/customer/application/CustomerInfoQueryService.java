package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.response.CustomerInfoResponse;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.domain.port.LoadCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerInfoQueryService {
    private final CustomerService customerService;

    public CustomerInfoResponse getCustomerInfo(Long customerId) {
        Customer customer = customerService.loadCustomer(customerId);
        return CustomerInfoResponse.of(customer);
    }
}
