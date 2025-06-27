package com.breaditnow.owner.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.owner.domain.model.Owner;
import com.breaditnow.owner.domain.port.in.OwnerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/api/v1/owner")
@RequiredArgsConstructor
public class InternalOwnerController {
    private final OwnerUseCase ownerUseCase;

    @GetMapping("/{ownerId}/fcm-token")
    public ApiSuccessResponse<String> getOwnerFcmToken(@PathVariable Long ownerId) {
        String fcmToken = ownerUseCase.findOwnerById(ownerId)
                .map(Owner::getFcmToken)
                .orElse(null);

        return ApiSuccessResponse.of(fcmToken);
    }
}
