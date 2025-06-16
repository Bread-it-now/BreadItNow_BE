package com.breaditnow.customer.customer.domain.port;

public interface SaveCustomerRegionPort {
    void preference(Long customerId, String sidoCode, String gugunCode);
    void delete(Long customerId);
}
