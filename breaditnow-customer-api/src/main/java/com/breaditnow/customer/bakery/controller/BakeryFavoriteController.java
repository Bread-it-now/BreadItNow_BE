package com.breaditnow.customer.bakery.controller;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.bakery.controller.res.BakeryFavoritePageResponse;
import com.breaditnow.customer.bakery.service.BakeryFavoritePageService;
import com.breaditnow.customer.bakery.service.BakeryFavoriteService;
import com.breaditnow.customer.common.application.request.GeoPointRequest;
import com.breaditnow.customer.common.presentation.swagger.docs.BakeryFavoriteControllerDocs;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryFavoriteController implements BakeryFavoriteControllerDocs {
    private final BakeryFavoriteService bakeryFavoriteService;
    private final BakeryFavoritePageService bakeryFavoritePageService;

    @PostMapping("/{bakeryId}/favorite")
    public ApiSuccessResponse<Map<String, Long>> likeBakery(@AuthCustomer Long customerId,
                                                            @PathVariable("bakeryId") Long bakeryId) {
        Long savedBakeryId = bakeryFavoriteService.likeBakery(customerId, bakeryId);
        return ApiSuccessResponse.of("bakeryId", savedBakeryId);
    }

    @DeleteMapping("/{bakeryId}/favorite")
    public ApiSuccessResponse<Map<String, Long>> deleteBakery(@AuthCustomer Long customerId,
                                                              @PathVariable("bakeryId") Long bakeryId) {
        Long savedBakeryId = bakeryFavoriteService.deleteBakery(customerId, bakeryId);
        return ApiSuccessResponse.of("bakeryId", savedBakeryId);
    }

    @GetMapping("/favorite")
    public ApiSuccessResponse<BakeryFavoritePageResponse> getFavorites(@AuthCustomer Long customerId,
                                                                       @RequestParam(name = "page", defaultValue = "0") int page,
                                                                       @RequestParam(name = "size", defaultValue = "10") int size,
                                                                       @RequestParam(name = "sort", defaultValue = "latest") String sort,
                                                                       @Valid GeoPointRequest geoPointRequest) {
        return ApiSuccessResponse.of(
                bakeryFavoritePageService.getFavorites(customerId, page, size, sort, geoPointRequest));
    }
}
