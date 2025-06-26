package com.breaditnow.product.adapter.in;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.product.application.dto.response.ProductInfoResponse;
import com.breaditnow.product.domain.port.in.GetProductInfoListUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/internal/api/v1/bakery/{bakeryId}/product")
@RequiredArgsConstructor
public class InternalProductController {
    private final GetProductInfoListUseCase getProductInfoListUseCase;

    @GetMapping
    public ApiSuccessResponse<List<ProductInfoResponse>> getProductInfoList(@PathVariable("bakeryId") Long bakeryId, @RequestParam List<Long> ids) {
        List<ProductInfoResponse> responses = getProductInfoListUseCase.findAllByIds(ids, bakeryId)
                .stream()
                .map(ProductInfoResponse::from)
                .collect(Collectors.toList());

        return ApiSuccessResponse.of(responses);
    }
}
