package com.breaditnow.customer.alert.application.response;

public record CustomerDoNotDisturbToggleResponse(boolean active) {
    public static CustomerDoNotDisturbToggleResponse of(boolean active) {
        return new CustomerDoNotDisturbToggleResponse(active);
    }
}
