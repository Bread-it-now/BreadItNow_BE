package com.breaditnow.owner.adapter.out.persistence;

import com.breaditnow.owner.domain.model.Owner;
import com.breaditnow.owner.domain.port.out.OwnerRepository;
import com.breaditnow.owner.adapter.out.persistence.entity.OwnerEntity;
import com.breaditnow.owner.adapter.out.persistence.repository.JpaOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OwnerPersistenceAdapter implements OwnerRepository {
    private final JpaOwnerRepository jpaOwnerRepository;

    @Override
    public Owner save(Owner owner) {
        OwnerEntity entity = OwnerEntity.from(owner);
        return jpaOwnerRepository.save(entity).toDomain();
    }

    @Override
    public Optional<Owner> findById(Long id) {
        return jpaOwnerRepository.findById(id)
                .map(OwnerEntity::toDomain);
    }

    @Override
    public Optional<Owner> findByEmail(String email) {
        return jpaOwnerRepository.findByEmail(email)
                .map(OwnerEntity::toDomain);
    }
}
