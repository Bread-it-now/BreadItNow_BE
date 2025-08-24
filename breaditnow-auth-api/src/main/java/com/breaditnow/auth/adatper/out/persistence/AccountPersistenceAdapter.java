package com.breaditnow.auth.adatper.out.persistence;

import com.breaditnow.auth.adatper.out.persistence.entity.AccountEntity;
import com.breaditnow.auth.adatper.out.persistence.repository.JpaAccountRepository;
import com.breaditnow.auth.domain.model.Account;
import com.breaditnow.auth.domain.port.out.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountPersistenceAdapter implements AccountRepository {
    private final JpaAccountRepository jpaAccountRepository;

    @Override
    public Optional<Account> findById(Long id) {
        return jpaAccountRepository.findById(id)
                .map(AccountEntity::toDomain);
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountEntity.from(account);
        AccountEntity savedEntity = jpaAccountRepository.save(entity);
        return savedEntity.toDomain();
    }
}
