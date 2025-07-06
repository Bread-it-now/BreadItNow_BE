package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.domain.model.SocialAuth;

public interface SaveSocialAuthPort {
    SocialAuth save(SocialAuth socialAuth);
}
