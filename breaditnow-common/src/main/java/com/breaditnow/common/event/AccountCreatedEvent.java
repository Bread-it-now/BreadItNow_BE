package com.breaditnow.common.event;

import com.breaditnow.common.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountCreatedEvent {
    private Long accountId;
    private Role role;

    public AccountCreatedEvent(Long accountId, Role role) {
        this.accountId = accountId;
        this.role = role;
    }
}
