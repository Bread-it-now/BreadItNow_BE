package com.breaditnow.auth.application.port.in;

public interface ChangePasswordUseCase {
    void changePassword(Long accountId, String rawPassword);
}
