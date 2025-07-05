package com.breaditnow.notification.adapter.in.web.resolver;

import com.breaditnow.common.domain.Role;

public record AuthenticatedUser(Long userId, String role) {
    public boolean isCustomer() {
        return "CUSTOMER".equals(role);
    }
    public boolean isOwner() {
        return "OWNER".equals(role);
    }

    public Role getRole() {
        return Role.valueOf(role);
    }
}
