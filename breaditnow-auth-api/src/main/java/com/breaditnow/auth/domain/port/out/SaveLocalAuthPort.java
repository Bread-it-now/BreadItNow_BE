package com.breaditnow.auth.domain.port.out;

import com.breaditnow.auth.domain.model.LocalAuth;

public interface SaveLocalAuthPort {
    LocalAuth save(LocalAuth localAuth);
}
