package com.breaditnow.customer.customer.domain.port;

import com.breaditnow.customer.customer.domain.Customer;

public interface CustomerPort {
    Customer save(Customer customer);

    Customer findById(Long id);

    Boolean isExistNickName(String nickname);
}
