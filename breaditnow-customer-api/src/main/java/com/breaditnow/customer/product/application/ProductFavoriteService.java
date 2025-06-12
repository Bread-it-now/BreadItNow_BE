package com.breaditnow.customer.product.application;

import com.breaditnow.customer.common.domain.DomainEventPublisher;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.customer.product.domain.event.ProductFavoriteCreatedEvent;
import com.breaditnow.customer.product.domain.event.ProductFavoriteRemovedEvent;
import com.breaditnow.customer.product.domain.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.domain.port.LoadProductPort;
import com.breaditnow.customer.product.domain.port.SaveProductFavoritePort;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.domain.global.exception.DomainErrorCode.BREAD_FAVORITE_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProductFavoriteService {
    private final SaveProductFavoritePort saveProductFavoritePort;
    private final LoadProductFavoritePort loadProductFavoritePort;
    private final LoadProductPort loadProductPort;
    private final DomainEventPublisher domainEventPublisher;

    @Transactional
    public void addFavoriteProduct(Long customerId, Long productId) {
        Product product = loadProductPort.getProduct(productId);

        ProductFavorite productFavorite = loadProductFavoritePort.findProductFavorite(customerId, productId)
                .map(favorite -> {
                    favorite.activate();
                    return favorite;
                })
                .orElseGet(() -> ProductFavorite.create(customerId, product.getId()));

        saveProductFavoritePort.save(productFavorite);
        domainEventPublisher.publish(new ProductFavoriteCreatedEvent(product.getId()));
    }

    @Transactional
    public void removeFavoriteProduct(Long customerId, Long productId) {
        Product product = loadProductPort.getProduct(productId);

        ProductFavorite productFavorite = loadProductFavoritePort
                .findProductFavorite(customerId, product.getId())
                .orElseThrow(() -> new DomainException(BREAD_FAVORITE_NOT_FOUND));

        productFavorite.deactivate();

        saveProductFavoritePort.save(productFavorite);
        domainEventPublisher.publish(new ProductFavoriteRemovedEvent(product.getId()));
    }
}
