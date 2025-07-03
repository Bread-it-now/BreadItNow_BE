package com.breaditnow.product.application;

import com.breaditnow.common.dto.StockUpdateItem;
import com.breaditnow.common.event.StockUpdateResultEvent;
import com.breaditnow.product.domain.model.Product;
import com.breaditnow.product.domain.port.out.ProductEventPort;
import com.breaditnow.product.domain.port.out.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.breaditnow.common.event.StockUpdateResultEvent.Status.FAILURE;

@Service
@RequiredArgsConstructor
public class ProductStockService {
    private final ProductRepository productRepository;
    private final ProductEventPort productEventPort;

    @Transactional
    public void decreaseStock(Long reservationId, List<StockUpdateItem> stockUpdateItems) {
        try {
            for(StockUpdateItem item : stockUpdateItems) {
                Product product = productRepository.getById(item.productId());
                product.updateStock(product.getSalesPolicy().stock() - item.quantity());
                productRepository.save(product);
            }
        } catch (Exception e) {
            StockUpdateResultEvent result = new StockUpdateResultEvent(reservationId, null , FAILURE, e.getMessage());
            productEventPort.publishStockUpdateResult(result);
        }
    }
}
