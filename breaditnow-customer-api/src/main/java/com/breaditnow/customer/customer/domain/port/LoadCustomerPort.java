package com.breaditnow.customer.customer.domain.port;

import com.breaditnow.customer.customer.domain.Customer;

public interface LoadCustomerPort {
    Customer findById(Long id);
    Boolean isExistNickName(String nickname);
}
