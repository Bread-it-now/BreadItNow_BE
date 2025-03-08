package com.breaditnow.customer.domain.bakeryfavorite.controller.req.enumerate;

import static com.breaditnow.customer.global.exception.CustomerErrorCode.*;

import org.springframework.data.domain.Sort;

import com.breaditnow.customer.global.exception.CustomerException;

public enum BakeryFavoriteSortType {
	// modifiedAt 내림차순
	LATEST {
		@Override
		public Sort getSort() {
			return Sort.by("modifiedAt").descending();
		}
	},

	// likeCount 내림차순
	POPULAR {
		@Override
		public Sort getSort() {
			return Sort.by("likeCount").descending();
		}
	};

	public static BakeryFavoriteSortType of(String source) {
		switch (source.toLowerCase()) {
			case "latest":
				return LATEST;
			case "popular":
				return POPULAR;
			default:
				throw new CustomerException(SORT_CONDITION_NOT_FOUND);
		}
	}

	public abstract Sort getSort();
}
