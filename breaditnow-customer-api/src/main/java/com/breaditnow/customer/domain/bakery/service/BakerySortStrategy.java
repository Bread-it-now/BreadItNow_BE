package com.breaditnow.customer.domain.bakery.service;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.global.dto.BakeryDistanceDto;
import com.breaditnow.domain.global.dto.GeoPoint;
import com.breaditnow.domain.global.exception.DomainException;

public enum BakerySortStrategy {
	FAVORITE {
		@Override
		public Page<BakeryDistanceDto> search(Long customerId, Pageable pageable, GeoPoint geoPoint,
			BakeryRepository repository) {
			return repository.searchHotBakeriesByFavorite(customerId, pageable, geoPoint);
		}
	},
	RESERVATION {
		@Override
		public Page<BakeryDistanceDto> search(Long customerId, Pageable pageable, GeoPoint geoPoint,
			BakeryRepository repository) {
			return repository.searchHotBakeriesByReservation(customerId, pageable, geoPoint);
		}
	};

	public abstract Page<BakeryDistanceDto> search(Long customerId, Pageable pageable, GeoPoint geoPoint,
		BakeryRepository repository);

	public static BakerySortStrategy from(String sort) {
		if ("favorite".equalsIgnoreCase(sort)) {
			return FAVORITE;
		} else if ("reservation".equalsIgnoreCase(sort)) {
			return RESERVATION;
		}
		throw new DomainException(BAKERY_SORT_CONDITION_NOT_FOUND);
	}
}
