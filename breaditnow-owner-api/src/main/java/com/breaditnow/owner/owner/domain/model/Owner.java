package com.breaditnow.owner.owner.domain.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class Owner {
    private Long id;
    private String email;
    private String password;
    private String fcmToken;

    @Builder
    private Owner(Long id, String email, String password, String fcmToken) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fcmToken = fcmToken;
    }

    public void changeFcmToken(String token) {
        this.fcmToken = token;
    }

    public void changePassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
    }

}
