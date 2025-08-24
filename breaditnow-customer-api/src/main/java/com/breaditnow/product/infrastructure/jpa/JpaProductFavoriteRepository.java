package com.breaditnow.product.infrastructure.jpa;

import com.breaditnow.product.infrastructure.jpa.entity.ProductFavoriteEntity;
import com.breaditnow.product.infrastructure.jpa.entity.ProductFavoriteEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductFavoriteRepository extends JpaRepository<ProductFavoriteEntity, ProductFavoriteEntityId> {

}