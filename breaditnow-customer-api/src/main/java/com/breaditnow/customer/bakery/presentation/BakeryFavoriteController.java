package com.breaditnow.customer.bakery.presentation;


import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.customer.bakery.application.BakeryFavoriteService;
import com.breaditnow.customer.common.security.annotation.AuthCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bakery")
public class BakeryFavoriteController {
    private final BakeryFavoriteService bakeryFavoriteService;

    @PostMapping("/{bakeryId}/favorite")
    public ApiSuccessResponse<Void> addFavoriteBakery(@AuthCustomer Long customerId, @PathVariable("bakeryId") Long bakeryId) {
        bakeryFavoriteService.addFavoriteBakery(customerId, bakeryId);
        return ApiSuccessResponse.of();
    }

    @DeleteMapping("/{bakeryId}/favorite")
    public ApiSuccessResponse<Void> removeFavoriteBakery(@AuthCustomer Long customerId, @PathVariable("bakeryId") Long bakeryId) {
        bakeryFavoriteService.removeFavoriteBakery(customerId, bakeryId);
        return ApiSuccessResponse.of();
    }
}
