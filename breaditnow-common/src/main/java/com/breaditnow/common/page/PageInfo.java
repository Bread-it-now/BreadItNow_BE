package com.breaditnow.common.page;

public record PageInfo(long totalElements, int totalPages, boolean isLast, int currPage) {
	public static PageInfo of(long totalElements, int totalPages, boolean isLast, int currPage) {
		return new PageInfo(totalElements, totalPages, isLast, currPage);
	}
}
