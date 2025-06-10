package com.breaditnow.customer.product.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductFavoriteRepository extends JpaRepository<ProductFavoriteEntity, ProductFavoriteEntityId> {

}