package com.breaditnow.domain.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.owner.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
