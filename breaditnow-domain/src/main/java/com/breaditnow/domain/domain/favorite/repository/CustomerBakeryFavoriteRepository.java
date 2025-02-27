package com.breaditnow.domain.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.favorite.entity.CustomerBakeryFavorite;

public interface CustomerBakeryFavoriteRepository extends JpaRepository<CustomerBakeryFavorite, Long> {
}
