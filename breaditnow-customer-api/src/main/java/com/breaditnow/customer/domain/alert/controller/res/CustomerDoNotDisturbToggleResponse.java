package com.breaditnow.customer.domain.alert.controller.res;

public record CustomerDoNotDisturbToggleResponse(boolean active) {
    public static CustomerDoNotDisturbToggleResponse of(boolean active) {
        return new CustomerDoNotDisturbToggleResponse(active);
    }
}
