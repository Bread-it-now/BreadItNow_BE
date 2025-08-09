package com.breaditnow.customer.domain.port.out;

public interface CustomerRegionRepository {
    void preference(Long customerId, String sidoCode, String gugunCode);
    void delete(Long customerId);
}
