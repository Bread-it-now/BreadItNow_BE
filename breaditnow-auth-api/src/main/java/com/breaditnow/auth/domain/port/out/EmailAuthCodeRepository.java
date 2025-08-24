package com.breaditnow.auth.domain.port.out;

import java.time.Duration;
import java.util.Optional;

public interface EmailAuthCodeRepository {
    void save(String email, String code, Duration ttl);
    Optional<String> find(String email);
    void delete(String email);
}
