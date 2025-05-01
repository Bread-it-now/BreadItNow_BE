package com.breaditnow.customer.domain.bakery.service;

import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BreadReleaseScheduleResponse;
import com.breaditnow.customer.domain.product.controller.res.ProductResponse;
import com.breaditnow.domain.domain.alert.repository.CustomerProductAlertRepository;
import com.breaditnow.domain.domain.bakery.entity.Bakery;
import com.breaditnow.domain.domain.bakery.repository.BakeryRepository;
import com.breaditnow.domain.domain.favorite.repository.customerbakeryfavorite.CustomerBakeryFavoriteRepository;
import com.breaditnow.domain.domain.favorite.repository.customerproductfavorite.CustomerProductFavoriteRepository;
import com.breaditnow.domain.domain.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.customer.domain.bakery.controller.res.BreadReleaseScheduleResponse.groupReleaseSchedules;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BakeryService {
    private final BakeryRepository bakeryRepository;
    private final CustomerBakeryFavoriteRepository customerBakeryFavoriteRepository;
    private final CustomerProductAlertRepository alertRepository;
    private final CustomerProductFavoriteRepository favoriteRepository;

    public BakeryDetailResponse getBakeryDetail(Long customerId, Long bakeryId) {
        Bakery bakery = bakeryRepository.getByIdAndIsActiveTrue(bakeryId);
        boolean isBakeryFavorite = customerBakeryFavoriteRepository
                .findByCustomerIdAndBakeryId(customerId, bakeryId)
                .isPresent();


        List<Product> products = bakery.getProducts().stream()
                .filter(Product::isActive)
                .filter(o -> !o.isHidden())
                .toList();

        List<ProductResponse> productResponses = products.stream()
                .map(product -> {
                    boolean alertAccepted = alertRepository.existsByCustomerIdAndProductId(customerId, product.getId());
                    boolean isProductFavorite = favoriteRepository.existsByCustomerIdAndProductId(customerId, product.getId());
                    return ProductResponse.of(product, alertAccepted, isProductFavorite);
                })
                .toList();

        List<BreadReleaseScheduleResponse> releaseSchedulesResponse = groupReleaseSchedules(products);
        return BakeryDetailResponse.of(BakeryResponse.of(bakery, isBakeryFavorite), productResponses, releaseSchedulesResponse);
    }
}
