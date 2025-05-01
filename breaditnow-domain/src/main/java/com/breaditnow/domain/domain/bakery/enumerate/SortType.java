package com.breaditnow.domain.domain.bakery.enumerate;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import com.breaditnow.domain.global.exception.DomainException;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum SortType {
	DISTANCE, // 거리순 정렬
	LATEST,   // 최신순 정렬
	POPULAR;   // 인기순 정렬

	@JsonCreator
	public static SortType from(String type) {
		try {
			return SortType.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException ex) {
			throw new DomainException(SORT_CONDITION_NOT_FOUND);
		}
	}
}
