package com.breaditnow.customer.product.application;

import com.breaditnow.customer.common.domain.Events;
import com.breaditnow.customer.common.exception.CustomerErrorCode;
import com.breaditnow.customer.common.exception.CustomerException;
import com.breaditnow.customer.product.domain.Product;
import com.breaditnow.customer.product.domain.ProductFavorite;
import com.breaditnow.customer.product.domain.event.ProductFavoriteCreatedEvent;
import com.breaditnow.customer.product.domain.event.ProductFavoriteRemovedEvent;
import com.breaditnow.customer.product.domain.port.LoadProductFavoritePort;
import com.breaditnow.customer.product.domain.port.LoadProductPort;
import com.breaditnow.customer.product.domain.port.SaveProductFavoritePort;
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
