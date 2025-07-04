package com.breaditnow.product.application;

import com.breaditnow.common.dto.StockUpdateItem;
import com.breaditnow.common.event.StockDecreaseRequestedEvent;
import com.breaditnow.common.event.StockIncreaseRequestedEvent;
import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.port.out.ProductEventPort;
import com.breaditnow.product.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.breaditnow.common.domain.OperationType.DECREASE;
import static com.breaditnow.common.domain.OperationType.INCREASE;
import static com.breaditnow.common.event.StockUpdateResultEvent.Status.FAILURE;
import static com.breaditnow.common.event.StockUpdateResultEvent.Status.SUCCESS;

@Service
@RequiredArgsConstructor
public class ProductStockService {
    private final ProductRepository productRepository;
    private final ProductEventPort productEventPort;

    @Transactional
    public void decreaseStock(StockDecreaseRequestedEvent event) {
        try {
            for(StockUpdateItem item : event.stockUpdateItems()) {
                Product product = productRepository.getById(item.productId());
                product.updateStock(product.getSalesPolicy().stock() - item.quantity());
                productRepository.save(product);
            }
            StockUpdateResultEvent result = new StockUpdateResultEvent(event.reservationId(), event.initiator(), event.reservationStatus(), event.stockUpdateItems(), SUCCESS, event.cancelReason());
            productEventPort.publishStockUpdateResult(result, DECREASE);
        } catch (Exception e) {
            StockUpdateResultEvent result = new StockUpdateResultEvent(event.reservationId(), event.initiator(), event.reservationStatus(), event.stockUpdateItems(), FAILURE, e.getMessage());
            productEventPort.publishStockUpdateResult(result, DECREASE);
        }
    }

    @Transactional
    public void increaseStock(StockIncreaseRequestedEvent event) {
        try {
            for(StockUpdateItem item : event.stockUpdateItems()) {
                Product product = productRepository.getById(item.productId());
                product.updateStock(product.getSalesPolicy().stock() + item.quantity());
                productRepository.save(product);
            }
            StockUpdateResultEvent result = new StockUpdateResultEvent(event.reservationId(), event.initiator(), event.reservationStatus(), event.stockUpdateItems(), SUCCESS, event.cancelReason());
            productEventPort.publishStockUpdateResult(result, INCREASE);
        } catch (Exception e) {
            StockUpdateResultEvent result = new StockUpdateResultEvent(event.reservationId(), event.initiator(), event.reservationStatus(), event.stockUpdateItems(), FAILURE, e.getMessage());
            productEventPort.publishStockUpdateResult(result, INCREASE);
        }
    }
}
