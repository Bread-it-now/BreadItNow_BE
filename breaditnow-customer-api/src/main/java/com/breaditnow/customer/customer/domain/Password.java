package com.breaditnow.customer.customer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class Password {
    private String password;

    public boolean match(PasswordEncoder passwordEncoder, String password) {
        return this.value.equals(password);
    }
}
