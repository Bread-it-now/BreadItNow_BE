package com.breaditnow.common.domain;

public enum Role {
    CUSTOMER,
    OWNER;

    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}
