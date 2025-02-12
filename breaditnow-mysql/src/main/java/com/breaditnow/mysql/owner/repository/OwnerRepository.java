package com.breaditnow.mysql.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breaditnow.mysql.owner.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
