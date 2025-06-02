package com.breaditnow.customer.alert.infrastructure;

import com.breaditnow.customer.alert.domain.ProductAlert;
import com.breaditnow.customer.alert.domain.port.LoadProductAlertPort;
import com.breaditnow.customer.alert.domain.port.SaveProductAlertPort;
import com.breaditnow.customer.alert.infrastructure.jpa.JpaProductAlertRepository;
import com.breaditnow.customer.alert.infrastructure.jpa.ProductAlertEntity;
import com.breaditnow.domain.global.exception.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.breaditnow.domain.global.exception.DomainErrorCode.ALERT_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class ProductAlertAdapter implements LoadProductAlertPort, SaveProductAlertPort {
    private final JpaProductAlertRepository jpaProductAlertRepository;

    @Override
    public void save(ProductAlert productAlert) {
        ProductAlertEntity entity = new ProductAlertEntity(productAlert);
        jpaProductAlertRepository.save(entity);
    }

    @Override
    public void delete(ProductAlert productAlert) {
        ProductAlertEntity entity = new ProductAlertEntity(productAlert);
        jpaProductAlertRepository.deleteById(entity.getId());
    }

    @Override
    public boolean isAlerted(ProductAlert productAlert) {
        ProductAlertEntity entity = new ProductAlertEntity(productAlert);
        return jpaProductAlertRepository.existsById(entity.getId());
    }

    @Override
    public ProductAlert findById(ProductAlert productAlert) {
        ProductAlertEntity entity = new ProductAlertEntity(productAlert);
        return  jpaProductAlertRepository.findById(entity.getId())
                .map(ProductAlertEntity::toDomain)
                .orElseThrow(() -> new DomainException(ALERT_NOT_FOUND));
    }
}
