package com.breaditnow.customer.domain.bakery.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import com.breaditnow.domain.domain.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryService {
	private final BakeryRepository bakeryRepository;
	private final ProductRepository productRepository;

	public BakeryDetailResponse getBakeryDetail(Long bakeryId) {
		Bakery bakery = bakeryRepository.getById(bakeryId);
		List<Product> products = productRepository.findByBakeryId(bakeryId);
		return BakeryDetailResponse.of(bakery, products);
	}
}
