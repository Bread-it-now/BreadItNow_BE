package com.breaditnow.auth.application;

import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;
import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.auth.domain.model.LocalAuth;
import com.breaditnow.auth.domain.port.out.AccountRepository;
import com.breaditnow.auth.domain.port.out.AuthTokenRepository;
import com.breaditnow.auth.domain.port.out.LocalAuthRepository;
import com.breaditnow.common.domain.Role;
import com.breaditnow.common.exception.AuthErrorCode;
import com.breaditnow.common.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.AuthErrorCode.EMAIL_ALREADY_EXISTS;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthTokenRepository authTokenRepository;
    private final LocalAuthRepository localAuthRepository;

    public Long signUp(DirectSignUpRequest request) {
        if (localAuthRepository.findByEmail(request.email()).isPresent()) {
            throw new AuthException(EMAIL_ALREADY_EXISTS);
        }

        Account newAccount = Account.create(Role.valueOf(request.role()));
        Account savedAccount = accountRepository.save(newAccount);

        String hashedPassword = passwordEncoder.encode(request.password());

        LocalAuth newLocalAuth = LocalAuth.create(request.email(), hashedPassword, savedAccount.getId());
        return localAuthRepository.save(newLocalAuth).getAccountId();
    }

    public void logout(Long userId){
        authTokenRepository.deleteRefreshToken(userId);
    }


    public Boolean verifyPassword(Long accountId, String password) {
        LocalAuth localAuth = localAuthRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AuthException(AuthErrorCode.USER_NOT_FOUND));

        return passwordEncoder.matches(password, localAuth.getPassword());
    }
}

