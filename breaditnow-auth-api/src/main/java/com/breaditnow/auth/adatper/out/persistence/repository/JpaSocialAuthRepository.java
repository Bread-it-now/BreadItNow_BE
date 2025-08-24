package com.breaditnow.auth.adatper.out.persistence.repository;

import com.breaditnow.auth.adatper.out.persistence.entity.SocialAuthEntity;
import com.breaditnow.auth.domain.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSocialAuthRepository extends JpaRepository<SocialAuthEntity, Long> {
    Optional<SocialAuthEntity> findByProviderAndProviderId(Provider provider, String providerId);
}
