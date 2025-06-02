package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.request.PasswordVerifyRequest;
import com.breaditnow.customer.customer.application.response.PasswordVerifyResponse;
import com.breaditnow.customer.customer.domain.Customer;
import com.breaditnow.customer.customer.domain.port.LoadCustomerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordVerifyQueryService {
    private final LoadCustomerPort loadCustomerPort;
    private final PasswordEncoder passwordEncoder;

    public PasswordVerifyResponse verifyPassword(Long customerId, PasswordVerifyRequest dto) {
        Customer customer = loadCustomerPort.findById(customerId);
        boolean verified = customer.isPasswordSame(passwordEncoder, dto.password());
        return new PasswordVerifyResponse(verified);
    }
}
