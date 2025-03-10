package com.breaditnow.owner.domain.breadcategory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;
import com.breaditnow.domain.domain.breadcategory.repository.BreadCategoryRepository;
import com.breaditnow.owner.domain.breadcategory.controller.res.BreadCategoryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BreadCategoryService {

	private final BreadCategoryRepository breadCategoryRepository;

	public List<BreadCategoryResponse> searchBreadCategories(String keyword, int size) {
		List<BreadCategory> breadCategories = breadCategoryRepository.search(keyword, size);
		return breadCategories.stream()
			.map(BreadCategoryResponse::of)
			.toList();
	}
}
