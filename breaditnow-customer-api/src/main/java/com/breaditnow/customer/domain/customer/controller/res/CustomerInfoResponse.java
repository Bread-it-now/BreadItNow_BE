package com.breaditnow.customer.domain.customer.controller.res;

import com.breaditnow.domain.domain.customer.entity.Customer;
import com.breaditnow.domain.domain.customer.enumerate.Provider;

public record CustomerInfoResponse(
        Long customerId,
        String email,
        String nickname,
        String phone,
        String profileImage,
        boolean isSocialLogin
) {

    public static CustomerInfoResponse of(Customer customer) {
        return new CustomerInfoResponse(
                customer.getId(),
                customer.getEmail(),
                customer.getNickname(),
                customer.getPhone(),
                customer.getProfileImage(),
                customer.getProvider() != Provider.EMAIL
        );
    }
}