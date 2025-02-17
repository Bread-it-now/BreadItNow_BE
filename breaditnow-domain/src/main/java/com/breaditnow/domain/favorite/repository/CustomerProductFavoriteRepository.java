package com.breaditnow.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.favorite.entity.CustomerProductFavorite;

public interface CustomerProductFavoriteRepository extends JpaRepository<CustomerProductFavorite, Long> {
}
