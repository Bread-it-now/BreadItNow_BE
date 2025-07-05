package com.breaditnow.auth.adatper.out.persistence.repository;

import com.breaditnow.auth.adatper.out.persistence.entity.LocalAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLocalAuthRepository extends JpaRepository<LocalAuthEntity, Long> {
    Optional<LocalAuthEntity> findByEmail(String email);
}
