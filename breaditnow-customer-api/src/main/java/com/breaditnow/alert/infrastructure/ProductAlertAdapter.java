package com.breaditnow.alert.infrastructure;

import com.breaditnow.alert.domain.ProductAlert;
import com.breaditnow.alert.domain.port.LoadProductAlertPort;
import com.breaditnow.alert.domain.port.SaveProductAlertPort;
import com.breaditnow.alert.infrastructure.jpa.JpaProductAlertRepository;
import com.breaditnow.alert.infrastructure.jpa.entity.ProductAlertEntity;
import com.breaditnow.alert.infrastructure.jpa.entity.ProductAlertEntityId;
//import com.breaditnow.customer.alert.infrastructure.jpa.query.QueryProductAlertRepository;
import com.breaditnow.alert.presentation.response.ProductAlertPageResponse;
import com.breaditnow.alert.presentation.response.TodayProductAlertListResponse;
import com.breaditnow.common.domain.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductAlertAdapter implements LoadProductAlertPort, SaveProductAlertPort {
    private final JpaProductAlertRepository jpaProductAlertRepository;
//    private final QueryProductAlertRepository queryProductAlertRepository;

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
//        return TodayProductAlertListResponse.of(queryProductAlertRepository.getTodayProductAlert(customerId));
        return null;
    }

    public ProductAlertPageResponse getProductAlerts(Long customerId, Pagination pagination) {
//        return ProductAlertPageResponse.of(queryProductAlertRepository.getProductAlerts(customerId, pagination));
        return null;
    }
}
