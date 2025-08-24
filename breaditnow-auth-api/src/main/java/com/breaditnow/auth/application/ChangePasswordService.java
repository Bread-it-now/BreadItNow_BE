package com.breaditnow.auth.application;

import com.breaditnow.auth.application.port.in.ChangePasswordUseCase;
import com.breaditnow.auth.domain.model.LocalAuth;
import com.breaditnow.auth.domain.port.out.LocalAuthRepository;
import com.breaditnow.common.exception.AuthErrorCode;
import com.breaditnow.common.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangePasswordService implements ChangePasswordUseCase {
    private final LocalAuthRepository localAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(Long accountId, String rawPassword) {
        LocalAuth localAuth = localAuthRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        localAuth.changePassword(rawPassword, passwordEncoder);
        localAuthRepository.save(localAuth);
    }
}
