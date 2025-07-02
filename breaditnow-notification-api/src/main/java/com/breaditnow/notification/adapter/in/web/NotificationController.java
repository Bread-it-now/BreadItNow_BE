package com.breaditnow.notification.adapter.in.web;

import com.breaditnow.common.response.ApiSuccessResponse;
import com.breaditnow.notification.adapter.in.web.resolver.AuthUser;
import com.breaditnow.notification.adapter.in.web.resolver.AuthenticatedUser;
import com.breaditnow.notification.application.dto.response.NotificationPageResponse;
import com.breaditnow.notification.domain.port.in.NotificationQueryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bakery/{bakeryId}/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationQueryUseCase notificationQueryUseCase;

    @GetMapping
    public ApiSuccessResponse<NotificationPageResponse> getNotifications(
            @AuthUser AuthenticatedUser user,
            @PathVariable Long bakeryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.of(notificationQueryUseCase.getNotifications(user, bakeryId, page, size));
    }
}
