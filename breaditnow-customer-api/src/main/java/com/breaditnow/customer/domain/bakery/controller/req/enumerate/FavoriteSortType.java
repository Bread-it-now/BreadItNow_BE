package com.breaditnow.customer.domain.bakery.controller.req.enumerate;

import org.springframework.data.domain.Sort;

public enum FavoriteSortType {
	// createdAt 내림차순
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

	public abstract Sort getSort();
}
