package com.breaditnow.bakery.adapter.in.web;

import com.breaditnow.bakery.application.dto.response.BakeryInfoResponse;
import com.breaditnow.bakery.domain.port.in.GetBakeryInfoUseCase;
import com.breaditnow.common.response.ApiSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/bakery")
@RequiredArgsConstructor
public class InternalBakeryController {
    private final GetBakeryInfoUseCase getBakeryInfoUseCase;

    @GetMapping("/{bakeryId}")
    public ApiSuccessResponse<BakeryInfoResponse> getBakeryInfo(@PathVariable("bakeryId") Long bakeryId) {
        return ApiSuccessResponse.of(getBakeryInfoUseCase.findById(bakeryId));
    }
}
