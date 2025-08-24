package com.breaditnow.auth.adatper.out.persistence;

import com.breaditnow.auth.adatper.out.persistence.entity.SocialAuthEntity;
import com.breaditnow.auth.adatper.out.persistence.repository.JpaSocialAuthRepository;
import com.breaditnow.auth.domain.model.Provider;
import com.breaditnow.auth.domain.model.SocialAuth;
import com.breaditnow.auth.domain.port.out.SocialAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SocialAuthPersistenceAdapter implements SocialAuthRepository {
    private final JpaSocialAuthRepository jpaSocialAuthRepository;

    @Override
    public Optional<SocialAuth> findByProviderAndProviderId(Provider provider, String providerId) {
        return jpaSocialAuthRepository.findByProviderAndProviderId(provider, providerId)
                .map(SocialAuthEntity::toDomain);
    }

    @Override
    public SocialAuth save(SocialAuth socialAuth) {
        SocialAuthEntity entity = SocialAuthEntity.from(socialAuth);
        return jpaSocialAuthRepository.save(entity).toDomain();
    }
}
