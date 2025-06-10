package com.breaditnow.customer.alert.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductAlertRepository extends JpaRepository<ProductAlertEntity, ProductAlertEntityId> {
}
