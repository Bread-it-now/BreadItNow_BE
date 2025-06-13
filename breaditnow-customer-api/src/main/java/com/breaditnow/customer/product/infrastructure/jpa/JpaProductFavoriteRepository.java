package com.breaditnow.customer.product.infrastructure.jpa;

import com.breaditnow.customer.product.infrastructure.jpa.entity.ProductFavoriteEntity;
import com.breaditnow.customer.product.infrastructure.jpa.entity.ProductFavoriteEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductFavoriteRepository extends JpaRepository<ProductFavoriteEntity, ProductFavoriteEntityId> {

}