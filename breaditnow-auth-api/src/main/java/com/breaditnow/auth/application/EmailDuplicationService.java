package com.breaditnow.auth.application;

import com.breaditnow.auth.domain.port.out.LocalAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailDuplicationService {
    private final LocalAuthRepository localAuthRepository;

    public boolean isDuplicated(String email) {
        return localAuthRepository.findByEmail(email).isPresent();
    }
}
