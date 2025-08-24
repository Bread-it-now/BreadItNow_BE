package com.breaditnow.auth.adatper.in.security.service;

import com.breaditnow.auth.adatper.in.security.AccountContext;
import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.auth.domain.model.LocalAuth;
import com.breaditnow.auth.domain.port.out.AccountRepository;
import com.breaditnow.auth.domain.port.out.LocalAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrincipalDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final LocalAuthRepository localAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LocalAuth localAuth = localAuthRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email));

        Account account = accountRepository.findById(localAuth.getAccountId())
                .orElseThrow(() -> new UsernameNotFoundException("계정을 찾을 수 없습니다: " + localAuth.getAccountId()));

        return new AccountContext(account, localAuth.getPassword());
    }
}
