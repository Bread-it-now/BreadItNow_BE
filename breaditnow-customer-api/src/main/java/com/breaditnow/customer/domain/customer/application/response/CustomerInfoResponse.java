package com.breaditnow.customer.domain.customer.application.response;

import com.breaditnow.customer.domain.customer.core.Customer;

import static com.breaditnow.customer.domain.customer.core.Provider.EMAIL;

public record CustomerInfoResponse(Long customerId, String email, String nickname, String phone, String profileImage, boolean isSocialLogin) {
    public static CustomerInfoResponse of(Customer customer) {
        return new CustomerInfoResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getNickname(),
                customer.getPhone(),
                customer.getProfileImageUrl(),
                customer.getProvider() != EMAIL
        );
    }
}
