package com.breaditnow.bakery.domain.port.in;

import com.breaditnow.bakery.application.dto.response.BakeryInfoResponse;

public interface GetBakeryInfoUseCase {
    BakeryInfoResponse findById(Long bakeryId);
}
