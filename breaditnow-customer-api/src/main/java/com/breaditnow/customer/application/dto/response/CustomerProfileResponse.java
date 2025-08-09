package com.breaditnow.customer.application.dto.response;

import com.breaditnow.customer.domain.model.Customer;

public record CustomerProfileResponse(Long id, String nickname) {
    public static CustomerProfileResponse from(Customer customer) {
        return new CustomerProfileResponse(customer.getId(), customer.getNickname());
    }
}
