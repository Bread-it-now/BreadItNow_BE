package com.breaditnow.auth.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LocalAuth {
    private final Long id; // Account ID와 동일
    private final String email;
    private final String password;
    private final Long accountId;
}
