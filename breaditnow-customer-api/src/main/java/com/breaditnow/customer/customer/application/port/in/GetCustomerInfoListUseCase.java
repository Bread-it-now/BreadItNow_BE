package com.breaditnow.customer.customer.application.port.in;

import com.breaditnow.customer.customer.application.response.CustomerProfileResponse;

import java.util.List;

public interface GetCustomerInfoListUseCase {
    List<CustomerProfileResponse> getCustomerInfoList(List<Long> customerIds);
}
