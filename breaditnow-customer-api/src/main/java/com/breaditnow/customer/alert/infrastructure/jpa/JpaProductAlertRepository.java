package com.breaditnow.customer.alert.infrastructure.jpa;

import com.breaditnow.customer.alert.infrastructure.jpa.entity.ProductAlertEntity;
import com.breaditnow.customer.alert.infrastructure.jpa.entity.ProductAlertEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductAlertRepository extends JpaRepository<ProductAlertEntity, ProductAlertEntityId> {
}
