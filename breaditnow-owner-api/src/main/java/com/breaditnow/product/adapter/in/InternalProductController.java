package com.breaditnow.product.adapter.in;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.product.application.dto.response.ProductInfoResponse;
import com.breaditnow.product.domain.port.in.GetProductInfoListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internal/api/v1/bakery/{bakeryId}/product")
@RequiredArgsConstructor
public class InternalProductController {
    private final GetProductInfoListUseCase getProductInfoListUseCase;

    @GetMapping
    public ApiSuccessResponse<List<ProductInfoResponse>> getProductInfoList(
            @PathVariable("bakeryId") Long bakeryId,
            @RequestParam List<Long> productIds
    ) {
        return ApiSuccessResponse.of(getProductInfoListUseCase.findAllByIds(productIds, bakeryId));
    }
}
