package com.breaditnow.domain.domain.breadcategory.repository;

import java.util.List;

import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;

public interface BreadCategoryRepositoryCustom {
	List<BreadCategory> search(String keyword, int size);
}
