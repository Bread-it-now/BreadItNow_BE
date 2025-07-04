package com.breaditnow.common.domain;

import com.breaditnow.common.exception.BreaditnowException;

import java.util.Arrays;

import static com.breaditnow.common.exception.CommonErrorCode.INVALID_PARAMETER;

public enum Role {
    CUSTOMER,
    OWNER,
    SYSTEM;

    public static Role from(String role) {
        if (role == null) {
            throw new BreaditnowException(INVALID_PARAMETER);
        }

        return Arrays.stream(values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new BreaditnowException(INVALID_PARAMETER));
    }
}
