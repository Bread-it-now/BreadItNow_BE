package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.domain.model.LocalAuth;

import java.util.Optional;

public interface LocalAuthRepository {
    Optional<LocalAuth> findByEmail(String email);
    LocalAuth save(LocalAuth localAuth);
}
