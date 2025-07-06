package com.breaditnow.auth.application;

import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;
import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.auth.domain.model.AccountStatus;
import com.breaditnow.auth.domain.model.LocalAuth;
import com.breaditnow.auth.domain.port.in.SignUpUseCase;
import com.breaditnow.auth.domain.port.out.SaveAccountPort;
import com.breaditnow.auth.domain.port.out.SaveLocalAuthPort;
import com.breaditnow.common.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {
    private final PasswordEncoder passwordEncoder;
    private final SaveAccountPort saveAccountPort;
    private final SaveLocalAuthPort saveLocalAuthPort;

    @Override
    public void signUp(DirectSignUpRequest request) {
        Account newAccount = Account.builder()
                .role(Role.valueOf(request.role()))
                .status(AccountStatus.ACTIVE)
                .build();

        Account savedAccount = saveAccountPort.save(newAccount);
        String hashedPassword = passwordEncoder.encode(request.password());

        LocalAuth newLocalAuth = LocalAuth.builder()
                .accountId(savedAccount.getId())
                .email(request.email())
                .password(hashedPassword)
                .build();

        saveLocalAuthPort.save(newLocalAuth);
    }
}
