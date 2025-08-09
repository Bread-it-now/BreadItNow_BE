package com.breaditnow.bakery.presentation;


import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.bakery.application.BakeryFavoriteService;
import com.breaditnow.bakery.application.request.BakeryFavoriteSearchCriteria;
import com.breaditnow.bakery.infrastructure.BakeryFavoriteAdapter;
import com.breaditnow.bakery.presentation.request.BakeryFavoriteSearchRequest;
import com.breaditnow.bakery.presentation.response.BakeryFavoritePageResponse;
import com.breaditnow.common.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryFavoriteController {
    private final BakeryFavoriteService bakeryFavoriteService;
    private final BakeryFavoriteAdapter bakeryFavoriteAdapter;

    @PostMapping("/{bakeryId}/favorite")
    public ApiSuccessResponse<Void> addFavoriteBakery(
            @AuthCustomer Long customerId,
            @PathVariable("bakeryId") Long bakeryId) {
        bakeryFavoriteService.addFavoriteBakery(customerId, bakeryId);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/{bakeryId}/favorite")
    public ApiSuccessResponse<Void> removeFavoriteBakery(
            @AuthCustomer Long customerId,
            @PathVariable("bakeryId") Long bakeryId) {
        bakeryFavoriteService.removeFavoriteBakery(customerId, bakeryId);
        return ApiSuccessResponse.of();
    }

    @GetMapping("/favorite")
    public ApiSuccessResponse<BakeryFavoritePageResponse> getFavoriteBakery(
            @AuthCustomer Long customerId,
            BakeryFavoriteSearchRequest request) {
        BakeryFavoriteSearchCriteria bakeryFavoriteSearchCriteria = BakeryFavoriteSearchCriteria.of(request);
        return ApiSuccessResponse.of(bakeryFavoriteAdapter.getFavoriteBakeries(customerId, bakeryFavoriteSearchCriteria));
    }
}
