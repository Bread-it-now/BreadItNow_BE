package com.breaditnow.product.application;

import com.breaditnow.common.domain.OperationType;
import com.breaditnow.common.dto.StockUpdateItem;
import com.breaditnow.common.domain.UserIdentifier;
import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.port.out.ProductEventPort;
import com.breaditnow.product.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.event.StockUpdateResultEvent.Status.FAILURE;
import static com.breaditnow.common.event.StockUpdateResultEvent.Status.SUCCESS;

@Service
@RequiredArgsConstructor
public class ProductStockService {
    private final ProductRepository productRepository;
    private final ProductEventPort productEventPort;

    @Transactional
    public void decreaseStock(Long reservationId, UserIdentifier initiator, List<StockUpdateItem> stockUpdateItems) {
        try {
            for(StockUpdateItem item : stockUpdateItems) {
                Product product = productRepository.getById(item.productId());
                product.updateStock(product.getSalesPolicy().stock() - item.quantity());
                productRepository.save(product);
            }
            StockUpdateResultEvent result = new StockUpdateResultEvent(reservationId, initiator, SUCCESS, null);
            productEventPort.publishStockUpdateResult(result, OperationType.DECREASE);
        } catch (Exception e) {
            StockUpdateResultEvent result = new StockUpdateResultEvent(reservationId, initiator , FAILURE, e.getMessage());
            productEventPort.publishStockUpdateResult(result, OperationType.DECREASE);
        }
    }

    @Transactional
    public void increaseStock(Long reservationId, UserIdentifier initiator, List<StockUpdateItem> stockUpdateItems, String reason) {
        try {
            for(StockUpdateItem item : stockUpdateItems) {
                Product product = productRepository.getById(item.productId());
                product.updateStock(product.getSalesPolicy().stock() + item.quantity());
                productRepository.save(product);
            }
            StockUpdateResultEvent result = new StockUpdateResultEvent(reservationId, initiator, SUCCESS, reason);
            productEventPort.publishStockUpdateResult(result, OperationType.INCREASE);
        } catch (Exception e) {
            StockUpdateResultEvent result = new StockUpdateResultEvent(reservationId, initiator, FAILURE, e.getMessage());
            productEventPort.publishStockUpdateResult(result, OperationType.INCREASE);
        }
    }
}
