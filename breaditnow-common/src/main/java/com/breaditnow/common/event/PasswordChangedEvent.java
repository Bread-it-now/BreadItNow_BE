package com.breaditnow.common.event;

import lombok.Getter;

@Getter
public class PasswordChangedEvent {
    private final Long accountId;
    private final String newPassword;

    public PasswordChangedEvent(Long accountId, String newPassword) {
        this.accountId = accountId;
        this.newPassword = newPassword;
    }
}
