package com.breaditnow.customer.bakery.application;

import com.breaditnow.customer.bakery.domain.port.LoadBakeryPort;
import com.breaditnow.customer.bakery.presentation.response.BakeryDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BakeryQueryService {
    private final LoadBakeryPort loadBakeryPort;

    public BakeryDetailResponse getBakeryFullInfo(Long customerId, Long bakeryId) {
        return null;
    }
}
