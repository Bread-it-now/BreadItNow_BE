package com.breaditnow.auth.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocalAuth {
    private final Long id;
    private final String email;
    private final String password;
    private final Long accountId;
}
