package com.breaditnow.owner.product.application.port.out;

import com.breaditnow.owner.product.application.port.dto.event.ProductCreatedEvent;
import com.breaditnow.owner.product.application.port.dto.event.ProductDeletedEvent;
import com.breaditnow.owner.product.application.port.dto.event.ProductUpdatedEvent;

public interface PublishProductEventPort {
    void publishProductCreated(ProductCreatedEvent event);
    void publishProductUpdated(ProductUpdatedEvent event);
    void publishProductDeleted(ProductDeletedEvent event);
}
