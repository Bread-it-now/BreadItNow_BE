package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.PasswordVerifyRequest;
import com.breaditnow.customer.customer.application.response.PasswordVerifyResponse;
import com.breaditnow.customer.customer.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordVerifyQueryService {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;

    public PasswordVerifyResponse verifyPassword(Long customerId, PasswordVerifyRequest dto) {
        Customer customer = customerService.loadCustomer(customerId);
        boolean verified = customer.isPasswordSame(passwordEncoder, dto.password());
        return new PasswordVerifyResponse(verified);
    }
}
