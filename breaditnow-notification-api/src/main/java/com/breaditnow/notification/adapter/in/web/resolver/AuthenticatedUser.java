package com.breaditnow.notification.adapter.in.web.resolver;

public record AuthenticatedUser(Long userId, String role) {
    public boolean isCustomer() {
        return "CUSTOMER".equals(role);
    }
    public boolean isOwner() {
        return "OWNER".equals(role);
    }
}
