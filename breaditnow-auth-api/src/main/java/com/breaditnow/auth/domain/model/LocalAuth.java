package com.breaditnow.auth.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
public class LocalAuth {
    private final Long id;
    private final String email;
    private final Long accountId;
    private String password;

    public static LocalAuth create(String email, String password, Long accountId) {
        return LocalAuth.builder()
                .email(email)
                .password(password)
                .accountId(accountId)
                .build();
    }

    public void changePassword(String rawPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(rawPassword);
    }
}
