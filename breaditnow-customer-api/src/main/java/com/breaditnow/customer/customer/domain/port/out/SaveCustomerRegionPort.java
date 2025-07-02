package com.breaditnow.customer.customer.domain.port.out;

public interface SaveCustomerRegionPort {
    void preference(Long customerId, String sidoCode, String gugunCode);
    void delete(Long customerId);
}
