package com.breaditnow.owner.owner.infrastructure.adapter.out.persitence;

import com.breaditnow.owner.owner.domain.model.Owner;
import com.breaditnow.owner.owner.domain.port.out.OwnerRepository;
import com.breaditnow.owner.owner.infrastructure.adapter.out.persitence.entity.OwnerEntity;
import com.breaditnow.owner.owner.infrastructure.adapter.out.persitence.repository.JpaOwnerRepository;
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
