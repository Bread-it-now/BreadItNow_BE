package com.breaditnow.auth.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialAuth {
    private final Long id;
    private final Provider provider;
    private final String providerId;
    private final Long accountId;
}
