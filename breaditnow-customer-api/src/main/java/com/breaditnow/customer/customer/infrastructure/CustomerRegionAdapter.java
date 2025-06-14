package com.breaditnow.customer.customer.infrastructure;

import com.breaditnow.customer.customer.domain.port.LoadCustomerRegionPort;
import com.breaditnow.customer.customer.domain.port.SaveCustomerRegionPort;
import com.breaditnow.customer.customer.infrastructure.jpa.CustomerRegionEntity;
import com.breaditnow.customer.customer.infrastructure.jpa.JpaCustomerRegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRegionAdapter implements LoadCustomerRegionPort, SaveCustomerRegionPort {
    private final JpaCustomerRegionRepository jpaCustomerRegionRepository;

    @Override
    public void preference(Long customerId, String sidoCode, String gugunCode) {
        CustomerRegionEntity customerRegionEntity = new CustomerRegionEntity(customerId, sidoCode, gugunCode);
        jpaCustomerRegionRepository.save(customerRegionEntity);
    }
}
