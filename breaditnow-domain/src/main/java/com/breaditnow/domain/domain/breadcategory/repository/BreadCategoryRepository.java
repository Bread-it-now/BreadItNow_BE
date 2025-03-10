package com.breaditnow.domain.domain.breadcategory.repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;
import com.breaditnow.domain.global.exception.DomainException;

public interface BreadCategoryRepository extends JpaRepository<BreadCategory, Long>, BreadCategoryRepositoryCustom {

	default BreadCategory getById(Long id) {
		return findById(id)
			.orElseThrow(() -> new DomainException(BREAD_CATEGORY_NOT_FOUND));
	}
}
