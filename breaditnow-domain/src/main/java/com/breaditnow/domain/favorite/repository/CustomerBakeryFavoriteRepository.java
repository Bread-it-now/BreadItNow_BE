package com.breaditnow.domain.favorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.favorite.entity.CustomerBakeryFavorite;

public interface CustomerBakeryFavoriteRepository extends JpaRepository<CustomerBakeryFavorite, Long> {
}
