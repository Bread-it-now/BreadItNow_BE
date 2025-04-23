package com.breaditnow.auth.domain.auth.service;

import com.breaditnow.domain.domain.customer.repository.CustomerRepository;
import com.breaditnow.domain.domain.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailDuplicationService {

    private final CustomerRepository customerRepository;
    private final OwnerRepository ownerRepository;

    public boolean isDuplicated(String email) {
        return customerRepository.existsByEmail(email) || ownerRepository.existsByEmail(email);
    }
}
