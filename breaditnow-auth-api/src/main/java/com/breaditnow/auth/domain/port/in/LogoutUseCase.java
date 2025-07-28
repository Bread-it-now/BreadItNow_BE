package com.breaditnow.auth.domain.port.in;

import jakarta.servlet.http.HttpServletResponse;

public interface LogoutUseCase {
    void logout(Long userId);
}
