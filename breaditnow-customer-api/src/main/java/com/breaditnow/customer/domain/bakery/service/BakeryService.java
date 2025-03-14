package com.breaditnow.customer.domain.bakery.service;

import static com.breaditnow.customer.domain.bakery.controller.res.BreadReleaseScheduleResponse.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BreadReleaseScheduleResponse;
import com.breaditnow.customer.domain.product.controller.res.ProductResponse;
import com.breaditnow.domain.domain.alert.repository.CustomerProductAlertRepository;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryService {
	private final BakeryRepository bakeryRepository;
	private final CustomerProductAlertRepository alertRepository;
	private final CustomerProductFavoriteRepository favoriteRepository;

	public BakeryDetailResponse getBakeryDetail(Long customerId, Long bakeryId) {
		Bakery bakery = bakeryRepository.getByIdAndIsActiveTrue(bakeryId);

		List<ProductResponse> productResponses = bakery.getProducts().stream()
			.map(product -> {
				boolean alertAccepted = alertRepository.existsByCustomerIdAndProductId(customerId, product.getId());
				boolean favorite = favoriteRepository.existsByCustomerIdAndProductId(customerId, product.getId());
				return ProductResponse.of(product, alertAccepted, favorite);
			})
			.toList();

		List<BreadReleaseScheduleResponse> releaseSchedulesResponse = groupReleaseSchedules(bakery.getProducts());
		return BakeryDetailResponse.of(BakeryResponse.of(bakery), productResponses, releaseSchedulesResponse);
	}
}
