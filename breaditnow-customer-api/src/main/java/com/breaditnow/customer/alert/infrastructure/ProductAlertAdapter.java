package com.breaditnow.customer.alert.infrastructure;

import com.breaditnow.customer.alert.domain.ProductAlert;
import com.breaditnow.customer.alert.domain.port.LoadProductAlertPort;
import com.breaditnow.customer.alert.domain.port.SaveProductAlertPort;
import com.breaditnow.customer.alert.infrastructure.jpa.JpaProductAlertRepository;
import com.breaditnow.customer.alert.infrastructure.jpa.entity.ProductAlertEntity;
import com.breaditnow.customer.alert.infrastructure.jpa.entity.ProductAlertEntityId;
import com.breaditnow.customer.alert.infrastructure.jpa.query.QueryProductAlertRepository;
import com.breaditnow.customer.alert.presentation.response.ProductAlertPageResponse;
import com.breaditnow.customer.alert.presentation.response.TodayProductAlertListResponse;
import com.breaditnow.customer.common.domain.vo.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductAlertAdapter implements LoadProductAlertPort, SaveProductAlertPort {
    private final JpaProductAlertRepository jpaProductAlertRepository;
    private final QueryProductAlertRepository queryProductAlertRepository;

    @Override
    public void save(ProductAlert productAlert) {
        ProductAlertEntity entity = new ProductAlertEntity(productAlert);
        jpaProductAlertRepository.save(entity);
    }

    @Override
    public void delete(ProductAlert productAlert) {
        ProductAlertEntity entity = new ProductAlertEntity(productAlert);
        jpaProductAlertRepository.save(entity);
    }

    @Override
    public Optional<ProductAlert> loadProductAlert(Long customerId, Long productId) {
        ProductAlertEntityId productAlertEntityId = new ProductAlertEntityId(customerId, productId);
        return jpaProductAlertRepository.findById(productAlertEntityId)
                .map(ProductAlertEntity::toDomain);
    }

    public TodayProductAlertListResponse getTodayProductAlert(Long customerId) {
        return TodayProductAlertListResponse.of(queryProductAlertRepository.getTodayProductAlert(customerId));
    }

    public ProductAlertPageResponse getProductAlerts(Long customerId, Pagination pagination) {
        return ProductAlertPageResponse.of(queryProductAlertRepository.getProductAlerts(customerId, pagination));
    }
}
