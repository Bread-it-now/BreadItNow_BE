package com.breaditnow.customer.domain.bakeryfavorite.service.pagination.strategy;

import static org.springframework.data.domain.Sort.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.breaditnow.domain.domain.favorite.entity.BakeryFavorite;
import com.breaditnow.domain.domain.favorite.repository.BakeryFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LatestBakeryFavoriteSortStrategy implements BakeryFavoriteSortStrategy {
	@Override
	public Page<BakeryFavorite> getFavoritePage(BakeryFavoriteRepository repository, Long customerId,
		Pageable pageable) {
		Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), getSort());
		return repository.findAllByCustomerIdAndIsActiveTrue(customerId, pageableWithSort);
	}

	@Override
	public Sort getSort() {
		return by("modifiedAt").descending();
	}
}
