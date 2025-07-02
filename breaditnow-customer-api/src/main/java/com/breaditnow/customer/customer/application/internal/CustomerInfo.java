package com.breaditnow.customer.customer.application.internal;

import com.breaditnow.customer.customer.domain.model.Customer;

public record CustomerInfo(
        Long customerId,
        String nickname,
        String phoneNumber
) {
    public static CustomerInfo of(Customer customer) {
        return new CustomerInfo(customer.getId(), customer.getNickname(), customer.getPhone());
    }
}
