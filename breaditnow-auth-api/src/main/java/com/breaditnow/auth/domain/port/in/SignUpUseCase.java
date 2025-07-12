package com.breaditnow.auth.domain.port.in;

import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;

public interface SignUpUseCase {
    Long signUp(DirectSignUpRequest request);
}
