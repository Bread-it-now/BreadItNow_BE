package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.domain.model.Account;

public interface SaveAccountPort {
    Account save(Account account);
}
