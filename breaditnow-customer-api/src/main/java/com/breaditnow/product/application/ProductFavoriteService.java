package com.breaditnow.product.application;

import com.breaditnow.common.domain.Events;
import com.breaditnow.common.exception.CustomerErrorCode;
import com.breaditnow.common.exception.CustomerException;
import com.breaditnow.product.domain.Product;
import com.breaditnow.product.domain.ProductFavorite;
import com.breaditnow.product.domain.event.ProductFavoriteCreatedEvent;
import com.breaditnow.product.domain.event.ProductFavoriteRemovedEvent;
import com.breaditnow.product.domain.port.LoadProductFavoritePort;
import com.breaditnow.product.domain.port.LoadProductPort;
import com.breaditnow.product.domain.port.SaveProductFavoritePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProductFavoriteService {
    private final SaveProductFavoritePort saveProductFavoritePort;
    private final LoadProductFavoritePort loadProductFavoritePort;
    private final LoadProductPort loadProductPort;

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
        Events.publish(new ProductFavoriteCreatedEvent(product.getId()));
    }

    @Transactional
    public void removeFavoriteProduct(Long customerId, Long productId) {
        Product product = loadProductPort.getProduct(productId);

        ProductFavorite productFavorite = loadProductFavoritePort
                .findProductFavorite(customerId, product.getId())
                .orElseThrow(() -> new CustomerException(CustomerErrorCode.BREAD_FAVORITE_NOT_FOUND));

        productFavorite.deactivate();

        saveProductFavoritePort.save(productFavorite);
        Events.publish(new ProductFavoriteRemovedEvent(product.getId()));
    }
}
