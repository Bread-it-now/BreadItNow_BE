//package com.breaditnow.owner.domain.product.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.breaditnow.domain.domain.breadcategory.entity.BreadCategory;
//import com.breaditnow.domain.domain.breadcategory.repository.BreadCategoryRepository;
//import com.breaditnow.domain.domain.product.entity.Product;
//import com.breaditnow.domain.domain.product.entity.ProductBreadCategory;
//import com.breaditnow.domain.domain.product.repository.ProductBreadCategoryRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class ProductBreadCategoryService {
//
//	private final BreadCategoryRepository categoryRepository;
//	private final ProductBreadCategoryRepository productBreadCategoryRepository;
//
//	@Transactional
//	public void addProductBreadCategories(Long[] breadCategoryIds, Product product) {
//		for (Long breadCategoryId : breadCategoryIds) {
//			BreadCategory breadCategory = categoryRepository.getById(breadCategoryId);
//			ProductBreadCategory productBreadCategory = ProductBreadCategory.builder()
//				.breadCategory(breadCategory)
//				.product(product)
//				.build();
//			productBreadCategoryRepository.save(productBreadCategory);
//		}
//	}
//}
