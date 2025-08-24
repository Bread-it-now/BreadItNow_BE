package com.breaditnow.auth.adatper.out.persistence.repository;

import com.breaditnow.auth.adatper.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAccountRepository extends JpaRepository<AccountEntity, Long> {

}
