package com.breaditnow.auth.application;

import com.breaditnow.auth.application.dto.request.DirectSignUpRequest;
import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.auth.domain.model.LocalAuth;
import com.breaditnow.auth.domain.port.out.AccountRepository;
import com.breaditnow.auth.domain.port.out.AuthTokenRepository;
import com.breaditnow.auth.domain.port.out.LocalAuthRepository;
import com.breaditnow.common.domain.Role;
import com.breaditnow.common.event.AccountCreatedEvent;
import com.breaditnow.common.exception.AuthException;
import com.breaditnow.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.exception.AuthErrorCode.EMAIL_ALREADY_EXISTS;
import static com.breaditnow.common.exception.AuthErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AuthTokenRepository authTokenRepository;
    private final LocalAuthRepository localAuthRepository;
    private final RabbitTemplate rabbitTemplate;

    public Long signUp(DirectSignUpRequest request) {
        if (localAuthRepository.findByEmail(request.email()).isPresent()) {
            throw new AuthException(EMAIL_ALREADY_EXISTS);
        }

        Account newAccount = Account.create(Role.valueOf(request.role()));
        Account savedAccount = accountRepository.save(newAccount);

        String hashedPassword = passwordEncoder.encode(request.password());
        localAuthRepository.save(LocalAuth.create(request.email(), hashedPassword, savedAccount.getId()));

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ACCOUNT_CREATED_ROUTING_KEY, new AccountCreatedEvent(savedAccount.getId(), Role.valueOf(request.role())));

        return savedAccount.getId();
    }

    public void logout(Long userId){
        authTokenRepository.deleteRefreshToken(userId);
    }


    public Boolean verifyPassword(Long accountId, String password) {
        LocalAuth localAuth = localAuthRepository.findByAccountId(accountId)
                .orElseThrow(() -> new AuthException(USER_NOT_FOUND));

        return passwordEncoder.matches(password, localAuth.getPassword());
    }
}

