package com.breaditnow.customer.product.application;

import com.breaditnow.customer.common.domain.DomainEventPublisher;
import com.breaditnow.customer.product.application.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.application.port.LoadProductPort;
import com.breaditnow.customer.product.application.port.SaveProductFavoritePort;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.customer.product.domain.event.ProductFavoriteCreatedEvent;
import com.breaditnow.customer.product.domain.event.ProductFavoriteRemovedEvent;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BREAD_FAVORITE_NOT_FOUND;
import static com.breaditnow.domain.global.exception.DomainErrorCode.PRODUCT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductFavoriteService {
    private final SaveProductFavoritePort saveProductFavoritePort;
    private final LoadProductFavoritePort loadProductFavoritePort;
    private final LoadProductPort loadProductPort;
    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public void addFavoriteProduct(Long customerId, Long productId) {
        validateProduct(productId);

        ProductFavorite productFavorite = loadProductFavoritePort
                .loadProductFavorite(customerId, productId)
                .map(favorite -> {
                    favorite.activate();
                    return favorite;
                })
                .orElseGet(() -> ProductFavorite.create(customerId, productId));

        saveProductFavoritePort.save(productFavorite);
        domainEventPublisher.publish(new ProductFavoriteCreatedEvent(productId));
    }


    @Transactional
    public void removeFavoriteProduct(Long customerId, Long productId) {
        validateProduct(productId);

        ProductFavorite productFavorite = loadProductFavoritePort
                .loadProductFavorite(customerId, productId)
                .orElseThrow(() -> new DomainException(BREAD_FAVORITE_NOT_FOUND));

        productFavorite.deactivate();

        saveProductFavoritePort.save(productFavorite);
        domainEventPublisher.publish(new ProductFavoriteRemovedEvent(productId));
    }

    private void validateProduct(Long productId) {
        if (loadProductPort.loadProduct(productId).isEmpty()) {
            throw new DomainException(PRODUCT_NOT_FOUND);
        }
    }
}
