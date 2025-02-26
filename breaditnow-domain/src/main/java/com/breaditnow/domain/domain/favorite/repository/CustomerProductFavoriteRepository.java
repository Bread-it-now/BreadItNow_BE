package com.breaditnow.domain.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.favorite.entity.CustomerProductFavorite;

public interface CustomerProductFavoriteRepository extends JpaRepository<CustomerProductFavorite, Long> {
}
