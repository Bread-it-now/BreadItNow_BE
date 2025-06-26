package com.breaditnow.customer.customer.application;

import com.breaditnow.customer.customer.application.port.in.GetCustomerInfoListUseCase;
import com.breaditnow.customer.customer.application.port.out.CustomerRepositoryPort;
import com.breaditnow.customer.customer.application.response.CustomerProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class GetCustomerInfoListService implements GetCustomerInfoListUseCase {

    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public List<CustomerProfileResponse> getCustomerInfoList(List<Long> customerIds) {
        return customerRepositoryPort.findAllByIdIn(customerIds).stream()
                .map(CustomerProfileResponse::from)
                .toList();
    }
}
