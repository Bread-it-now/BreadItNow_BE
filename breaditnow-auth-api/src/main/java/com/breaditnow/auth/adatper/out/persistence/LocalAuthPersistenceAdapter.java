package com.breaditnow.auth.adatper.out.persistence;

import com.breaditnow.auth.adatper.out.persistence.entity.LocalAuthEntity;
import com.breaditnow.auth.adatper.out.persistence.repository.JpaLocalAuthRepository;
import com.breaditnow.auth.domain.model.LocalAuth;
import com.breaditnow.auth.domain.port.out.LocalAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LocalAuthPersistenceAdapter implements LocalAuthRepository {
    private final JpaLocalAuthRepository jpaLocalAuthRepository;

    @Override
    public Optional<LocalAuth> findByEmail(String email) {
        return jpaLocalAuthRepository.findByEmail(email)
                .map(LocalAuthEntity::toDomain);
    }

    @Override
    public LocalAuth save(LocalAuth localAuth) {
        LocalAuthEntity entity = LocalAuthEntity.from(localAuth);
        return jpaLocalAuthRepository.save(entity).toDomain();
    }
}
