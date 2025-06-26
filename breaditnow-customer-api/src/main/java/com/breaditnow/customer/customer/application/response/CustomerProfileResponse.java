package com.breaditnow.customer.customer.application.response;

import com.breaditnow.customer.customer.domain.Customer;

public record CustomerProfileResponse(Long id, String nickname) {
    public static CustomerProfileResponse from(Customer customer) {
        return new CustomerProfileResponse(customer.getId(), customer.getNickname());
    }
}
