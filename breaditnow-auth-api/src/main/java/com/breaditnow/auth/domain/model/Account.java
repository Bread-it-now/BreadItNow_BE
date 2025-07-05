package com.breaditnow.auth.domain.model;

import com.breaditnow.common.domain.Role;

import java.util.List;

import static com.breaditnow.auth.domain.model.AccountStatus.ACTIVE;

public class Account {
    private Long id;
    private Role role;
    private LocalAuth account;
    private List<SocialAuth> socialAuths;
    private AccountStatus status;

    public boolean isActive() {
        return status == ACTIVE;
    }
}
