package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.domain.model.Provider;
import com.breaditnow.auth.domain.model.SocialAuth;

import java.util.Optional;

public interface SocialAuthRepository {
    Optional<SocialAuth> findByProviderAndProviderId(Provider provider, String providerId);

    SocialAuth save(SocialAuth socialAuth);
}
