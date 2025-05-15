package com.breaditnow.customer.domain.customer.application.port;

import com.breaditnow.customer.domain.customer.core.Customer;

public interface CustomerPort {
    Customer save(Customer customer);

    Customer findById(Long id);

    Boolean isExistNickName(String nickname);
}
