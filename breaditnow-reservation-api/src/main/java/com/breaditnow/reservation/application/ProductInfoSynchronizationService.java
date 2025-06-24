package com.breaditnow.reservation.application;

import com.breaditnow.reservation.application.dto.event.ProductCreatedEvent;
import com.breaditnow.reservation.application.dto.event.ProductDeletedEvent;
import com.breaditnow.reservation.application.dto.event.ProductUpdatedEvent;
import com.breaditnow.reservation.application.port.in.ProductInfoSynchronizationUseCase;
import com.breaditnow.reservation.application.port.out.ProductInfoRepositoryPort;
import com.breaditnow.reservation.domain.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductInfoSynchronizationService implements ProductInfoSynchronizationUseCase {
    private final ProductInfoRepositoryPort productInfoRepository;

    @Override
    @Transactional
    public void createProductInfo(ProductCreatedEvent event) {
        ProductInfo productInfo = ProductInfo.builder()
                .productId(event.productId())
                .bakeryId(event.bakeryId())
                .name(event.name())
                .imageUrl(event.imageUrl())
                .price(event.price().getAmount())
                .status(event.status())
                .isDeleted(false)
                .build();

        productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void updateProductInfo(ProductUpdatedEvent event) {
        productInfoRepository.findById(event.productId()).ifPresent(product -> {
            product.update(event.name(), event.imageUrl(), event.price().getAmount(), event.status());
            productInfoRepository.save(product);
        });
    }

    @Override
    @Transactional
    public void deleteProductInfo(ProductDeletedEvent event) {
        productInfoRepository.findById(event.productId()).ifPresent(product -> {
            product.delete();
            productInfoRepository.save(product);
        });
    }
}
