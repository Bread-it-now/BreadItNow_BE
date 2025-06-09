package com.breaditnow.customer.product.application.event;

import com.breaditnow.customer.product.domain.port.LoadProductPort;
import com.breaditnow.customer.product.domain.port.SaveProductPort;
import com.breaditnow.customer.product.domain.event.ProductFavoriteCreatedEvent;
import com.breaditnow.customer.product.domain.event.ProductFavoriteRemovedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductFavoriteEventHandler {
    private final SaveProductPort saveProductPort;
    private final LoadProductPort loadProductPort;

    @EventListener
    @Transactional
    public void handleProductFavoriteCreated(ProductFavoriteCreatedEvent event) {
        loadProductPort.findProduct(event.getProductId())
                .ifPresent(product -> {
                    product.favorite();
                    saveProductPort.save(product);
                });
    }

    @EventListener
    @Transactional
    public void handleProductFavoriteRemoved(ProductFavoriteRemovedEvent event) {
        loadProductPort.findProduct(event.getProductId())
                .ifPresent(product -> {
                    product.unfavorite();
                    saveProductPort.save(product);
                });
    }
}