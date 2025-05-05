package com.breaditnow.customer.domain.bakery.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.domain.bakery.controller.res.BakeryDetailResponse;
import com.breaditnow.customer.domain.bakery.controller.res.HotBakeryPageResponse;
import com.breaditnow.customer.domain.bakery.service.BakeryPageService;
import com.breaditnow.customer.domain.bakery.service.BakeryService;
import com.breaditnow.customer.domain.common.req.GeoPointRequest;
import com.breaditnow.customer.global.security.annotation.AuthCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryController implements BakeryControllerDocs {
    private final BakeryService bakeryService;
    private final BakeryPageService bakeryPageService;

    @GetMapping("/{bakeryId}/detail")
    public ApiSuccessResponse<BakeryDetailResponse> getBakeryDetail(@AuthCustomer Long customerId,
                                                                    @PathVariable("bakeryId") Long bakeryId) {
        return ApiSuccessResponse.of(bakeryService.getBakeryDetail(customerId, bakeryId));
    }

    @GetMapping("/hot")
    public ApiSuccessResponse<HotBakeryPageResponse> searchHotBakeries(
            @AuthCustomer Long customerId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sort", defaultValue = "reservation") String sort,
            @Valid GeoPointRequest geoPointRequest) {
        return ApiSuccessResponse.of(
                bakeryPageService.searchHotBakeries(customerId, page, size, sort, geoPointRequest));
    }
}
