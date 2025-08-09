package com.breaditnow.customer.customer.application.response;

import com.breaditnow.customer.customer.domain.model.Customer;

public record CustomerInfoResponse(Long customerId, String nickname, String phone, String profileImage) {
    public static CustomerInfoResponse of(Customer customer) {
        return new CustomerInfoResponse(
                customer.getId(),
                customer.getNickname(),
                customer.getPhone(),
                customer.getProfileImageUrl()
        );
    }
}
