package com.breaditnow.customer.domain.bakery.service;

import static com.breaditnow.customer.domain.bakery.controller.res.BreadReleaseScheduleResponse.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BreadReleaseScheduleResponse;
import com.breaditnow.customer.domain.bakery.controller.res.HotBakeryPageResponse;
import com.breaditnow.customer.domain.product.controller.res.ProductResponse;
import com.breaditnow.domain.domain.alert.repository.CustomerProductAlertRepository;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.product.entity.Product;

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

		List<Product> products = bakery.getProducts().stream()
			.filter(Product::isActive)
			.filter(o -> !o.isHidden())
			.toList();

		List<ProductResponse> productResponses = products.stream()
			.map(product -> {
				boolean alertAccepted = alertRepository.existsByCustomerIdAndProductId(customerId, product.getId());
				boolean favorite = favoriteRepository.existsByCustomerIdAndProductId(customerId, product.getId());
				return ProductResponse.of(product, alertAccepted, favorite);
			})
			.toList();

		List<BreadReleaseScheduleResponse> releaseSchedulesResponse = groupReleaseSchedules(products);
		return BakeryDetailResponse.of(BakeryResponse.of(bakery), productResponses, releaseSchedulesResponse);
	}

	public HotBakeryPageResponse searchHotBakeries(Long customerId, Pageable pageable) {
		Page<Bakery> bakeries = bakeryRepository.searchHotBakeries(customerId, pageable);
		return HotBakeryPageResponse.of(bakeries);
	}
}
