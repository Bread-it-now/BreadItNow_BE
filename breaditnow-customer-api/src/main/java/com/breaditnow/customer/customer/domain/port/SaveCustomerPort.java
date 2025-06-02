package com.breaditnow.customer.customer.domain.port;

import com.breaditnow.customer.customer.domain.Customer;

public interface SaveCustomerPort {
    Customer save(Customer customer);
}
