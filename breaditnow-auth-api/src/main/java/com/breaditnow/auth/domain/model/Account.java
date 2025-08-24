package com.breaditnow.auth.domain.model;

import com.breaditnow.common.domain.Role;
import lombok.Builder;
import lombok.Getter;

import static com.breaditnow.auth.domain.model.AccountStatus.ACTIVE;

@Getter
@Builder
public class Account {
    private Long id;
    private Role role;
    private AccountStatus status;

    public boolean isActive() {
        return status == ACTIVE;
    }

    public static Account create(Role role) {
        return Account.builder()
                .role(role)
                .status(ACTIVE)
                .build();
    }
}
