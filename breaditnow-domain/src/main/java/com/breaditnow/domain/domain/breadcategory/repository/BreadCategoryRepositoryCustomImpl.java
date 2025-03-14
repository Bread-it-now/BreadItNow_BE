package com.breaditnow.domain.domain.breadcategory.repository;

import static com.breaditnow.domain.domain.breadcategory.entity.QBreadCategory.*;
import static com.breaditnow.domain.domain.product.entity.QProductBreadCategory.*;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BreadCategoryRepositoryCustomImpl implements BreadCategoryRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<BreadCategory> search(String keyword, int size) {
		return queryFactory.selectFrom(breadCategory)
			.join(productBreadCategory)
			.on(breadCategory.id.eq(productBreadCategory.breadCategory.id))
			.where(keyword != null && !keyword.isEmpty()
				? breadCategory.name.containsIgnoreCase(keyword)
				: null)
			.groupBy(breadCategory.id)
			.orderBy(breadCategory.count().desc())
			.limit(size)
			.fetch();
	}
}
