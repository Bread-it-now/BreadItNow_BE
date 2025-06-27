package com.breaditnow.customer.customer.application.response;

import com.breaditnow.customer.customer.domain.model.Customer;

import static com.breaditnow.customer.customer.domain.model.Provider.EMAIL;

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
