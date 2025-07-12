package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.domain.model.Account;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(Long id);
    Account save(Account account);
}
