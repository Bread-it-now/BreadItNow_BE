package com.breaditnow.owner.domain.port.out;

import com.breaditnow.owner.domain.model.Owner;

import java.util.Optional;

public interface OwnerRepository {
    Owner save(Owner owner);
    Optional<Owner> findById(Long id);
    Optional<Owner> findByEmail(String email);
}
